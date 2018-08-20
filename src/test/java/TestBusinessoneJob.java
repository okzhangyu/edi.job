import com.google.gson.Gson;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Fancy
 * @date 2018/8/18
 */
public class TestBusinessoneJob extends TestCase {
    public class CodebarInfo{
        private String baseType;
        public String getBaseType(){
            return baseType;
        }
        public void setBaseType(String baseType){
            this.baseType = baseType;
        }
        private List<String> codebar;

        public List<String> getCodebar(){
            return codebar;
        }
        public void setCodebar(List<String> codebar){
            this.codebar = codebar;
        }

    }

    @Test
    public void testSerialCodeBar(){
        CodebarInfo codebar = new CodebarInfo();
        codebar.setBaseType("11");
        List<String> codeBar = new ArrayList<String>();
        codeBar.add("10001");
        codeBar.add("10002");
        codeBar.add("10003");
        codebar.setCodebar(codeBar);
        Gson gson = new Gson();
        String str = gson.toJson(codebar);
        System.out.println(str);
    }
}
