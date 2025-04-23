package TH.helpers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class JsonUtils {

    // Đọc dữ liệu JSON và trả về Map với tỉnh/thành phố và danh sách quận/huyện
    // Hàm đọc dữ liệu từ file JSON
    public static Map<String, List<String>> loadProvincesAndDistricts(String filePath) {
        try {
            FileReader reader = new FileReader(filePath);
            return new Gson().fromJson(reader, new TypeToken<Map<String, List<String>>>() {}.getType());
        } catch (Exception e) {
            throw new RuntimeException("Không thể đọc file JSON: " + filePath, e);
        }
    }

    public static Map<String, Map<String, List<String>>> loadProvincesDistrictsAndStores(String filePath) {
        try {
            FileReader reader = new FileReader(filePath);
            return new Gson().fromJson(reader, new TypeToken<Map<String, Map<String, List<String>>>>() {}.getType());
        } catch (Exception e) {
            throw new RuntimeException("Không thể đọc file JSON: " + filePath, e);
        }
    }
}
