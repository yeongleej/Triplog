package com.example.travel.route.service;

import com.example.travel.route.entity.SpotInfo;
import com.example.travel.route.repository.SpotInfoRepository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SpotInfoService {
    private final SpotInfoRepository sdao;
    public SpotInfoService(SpotInfoRepository sdao) {
        this.sdao = sdao;
    }

    @Value("${app.kakao.rest-api-key}")
    private String kakaoApiKey;

    // Kakao Map에서 받은 여러 개의 데이터를 현재 위치에서 가까운 순으로 정렬해서 리턴 or DB에 저장된 장소 정보 리턴
    public List<SpotInfo> locationInfoList(String loc, Double longitude, Double latitude) throws ParseException {
        List<SpotInfo> placeInfoList = new ArrayList<>();

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", "KakaoAK " + kakaoApiKey);

        String kakaoMapUrl = "https://dapi.kakao.com/v2/local/search/keyword.json";
        String requestUrl = UriComponentsBuilder.fromHttpUrl(kakaoMapUrl)
                .queryParam("query", loc).build().toUriString();

        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);
        ResponseEntity<String> placeInfos = restTemplate.exchange(requestUrl, HttpMethod.GET, entity, String.class);

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(placeInfos.getBody());
        JSONArray jsonArray = (JSONArray) jsonObject.get("documents");

        List<JSONObject> placeLists = new ArrayList<>();
        for (Object o : jsonArray) {
            placeLists.add((JSONObject) o);

        }
        // 반경 20km 이내의 지역에서 검색
        List<JSONObject> closePlaceLists = new ArrayList<>();
        for(JSONObject jo: placeLists){

            double longdiff = Math.abs(longitude - Double.parseDouble((String) jo.get("x")));
            double latdiff = Math.abs(latitude - Double.parseDouble((String) jo.get("y")));

            double sin = Math.sin(latdiff / 2) * Math.sin(latdiff / 2)
                    + Math.cos(Math.toRadians(Double.parseDouble((String) jo.get("y")))) * Math.cos(Math.toRadians(latitude))
                    * Math.sin(longdiff / 2) * Math.sin(longdiff / 2);

            double tan = 2 * Math.atan2(Math.sqrt(sin), Math.sqrt(1 - sin));

            double distance = 6371 * tan * 1000;

            if(distance <= 2000000) {
                closePlaceLists.add(jo);
            }
        }

        closePlaceLists.sort((a, b) -> {
            double aLong = Double.parseDouble((String) a.get("x"));
            double aLat = Double.parseDouble((String) a.get("y"));
            double bLong = Double.parseDouble((String) b.get("x"));
            double bLat = Double.parseDouble((String) b.get("y"));

            double longDiffA = Math.toRadians(longitude - aLong);
            double latDiffA = Math.toRadians(latitude - aLat);
            double longDiffB = Math.toRadians(longitude - bLong);
            double latDiffB = Math.toRadians(latitude - bLat);

            double sinA = Math.sin(latDiffA / 2) * Math.sin(latDiffA / 2)
                    + Math.cos(Math.toRadians(aLat)) * Math.cos(Math.toRadians(latitude))
                    * Math.sin(longDiffA / 2) * Math.sin(longDiffA / 2);
            double sinB = Math.sin(latDiffB / 2) * Math.sin(latDiffB / 2)
                    + Math.cos(Math.toRadians(bLat)) * Math.cos(Math.toRadians(latitude))
                    * Math.sin(longDiffB / 2) * Math.sin(longDiffB / 2);

            double tanA = 2 * Math.atan2(Math.sqrt(sinA), Math.sqrt(1 - sinA));
            double tanB = 2 * Math.atan2(Math.sqrt(sinB), Math.sqrt(1 - sinB));

            // 지구 반지름을 6371km로 가정하고 거리 계산
            double distanceA = 6371 * tanA * 1000;
            double distanceB = 6371 * tanB * 1000;

            return Double.compare(distanceA, distanceB);
        });


        for (JSONObject object : closePlaceLists) {
            if (object.get("road_address_name").equals("")) {
                placeInfoList.add(new SpotInfo((String) object.get("place_name"), (String) object.get("address_name"),
                        Double.parseDouble((String) object.get("x")), Double.parseDouble((String) object.get("y"))));
            }
            else {
                placeInfoList.add(new SpotInfo((String) object.get("place_name"), (String) object.get("road_address_name"),
                        Double.parseDouble((String) object.get("x")), Double.parseDouble((String) object.get("y"))));
            }
        }
        return placeInfoList;
    }

    @Transactional
    public List<SpotInfo> findAllSpotInfo(){
        return sdao.findAllSpotInfo();
    }

    @Transactional
    public SpotInfo findSpotInfoById(Long spot_id){
        return sdao.findSpotInfoById(spot_id);
    }

    @Transactional
    public List<SpotInfo> findSpotInfoByName(String spotName, Double longitude, Double latitude)
            throws SQLException, ParseException {

        List<SpotInfo> spotList = sdao.findSpotInfoByName(spotName);
        // 장소를 검색했을 때 db에 없음
        if(spotList.isEmpty()) {
            List<SpotInfo> newSpotList = locationInfoList(spotName, longitude, latitude);

            sdao.addSpotInfo(newSpotList);
            spotList = newSpotList;
        }
        return spotList;
    }

    @Transactional
    public List<SpotInfo> findSpotInfoAgain(String spotName, Double longitude, Double latitude) throws ParseException {

        List<SpotInfo> newSpotList = locationInfoList(spotName, longitude, latitude);
        List<SpotInfo> resSpotList = new ArrayList<>();
        if(!newSpotList.isEmpty()) {
            for(SpotInfo spot: newSpotList){
                try {
                    sdao.addOneSpotInfo(spot);
                    resSpotList.add(spot);
                }catch (DataAccessException e){
                    System.out.println("동일한 장소 입력 >> " + spot.getSpot_name());
                }
            }
        }
        return resSpotList;
    }

    @Transactional
    public int addSpotInfo(List<SpotInfo> spotInfoList){
        return sdao.addSpotInfo(spotInfoList);
    }

    @Transactional
    public int delete(Long spot_id){
        return sdao.delete(spot_id);
    }

    @Transactional
    public int modify(SpotInfo spot){
        return sdao.modify(spot);
    }
}