package com.glen.glengen.templates;/**
 * @author Glen
 * @create 2020- 09-2020/9/10-17:00
 * @Description
 */

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glen.glencommonsystem.util.DateUtils;
import com.glen.glencommonsystem.util.R;
import com.glen.glengen.util.ContentOperationUtil;
import com.glen.glengen.util.FileOperationUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Glen
 * @create 2020/9/10 17:00
 * @Description
 */
@Slf4j
public class EntityTemplate {

    public static R EntityTemplateWriteFile(JSONObject params) {
        //log.info("EntityTemplateWriteFile---params" + "---" + params);

        StringBuffer content = new StringBuffer("package " + params.getString("packageName") + ";" + "\n");
        content.append("/**\n" +
                " * @author " + params.getString("author") + "\n" +
                " * @create " + DateUtils.currentTime() + "\n" +
                " * @Description " + params.getString("Description") + "\n" +
                " */\n\n");
        content.append("import com.fasterxml.jackson.annotation.JsonFormat;\n" +
                "import lombok.Data;\n" +
                "\n" +
                "import javax.persistence.*;\n" +
                "import java.io.Serializable;\n" +
                "import javax.persistence.Entity;\n" +
                "import java.util.Date;\n\n");
        content.append("@Data\n" +
                "@Entity\n" +
                "@Table(name= \"" + FileOperationUtil.tableName(params.getString("className")) + "\")\n");
        content.append("public class " + params.getString("classNameStand") + " implements Serializable {\n");
        //循环输出变量
        // log.info("params" + params.getString("vars"));
        JSONArray array = params.getJSONArray("vars");
        // log.info("array:" + array);
        for (int i = 0; i < array.size(); i++) {
            JSONObject jo = array.getJSONObject(i);
            content.append("    /**\n" +
                    "     * " + jo.getString("note") + "\n" +
                    "     */\n");
            if ("1".equals(jo.getString("isPrimaryKey"))) {
                content.append("    @Id\n" + "    @GeneratedValue(strategy= GenerationType.AUTO)\n");
            }
            if ("Date".equals(jo.getString("varTpye"))) {
                content.append("    @JsonFormat(pattern = \"" + jo.getString("pattern") + "\")\n");
            }
            content.append("    public " + jo.getString("varTpye") + " " + jo.getString("varName") + ";\n\n");
        }
        content.append("    public " + params.getString("classNameStand") + "(");
        content.append(array.getJSONObject(0).getString("varTpye") + " " + array.getJSONObject(0).getString("varName")+", ");
        content.append(array.getJSONObject(1).getString("varTpye") + " " + array.getJSONObject(1).getString("varName")+") {\n");
        content.append("        this."+array.getJSONObject(0).getString("varName")+"= "+array.getJSONObject(0).getString("varName")+";\n");
        content.append("        this."+array.getJSONObject(1).getString("varName")+"= "+array.getJSONObject(1).getString("varName")+";\n");
        content.append("    }\n\n");
        content.append("    public " + params.getString("classNameStand") + "(){\n"+"   }\n");
        content.append("}");
        ContentOperationUtil.contentOperation(params,content);
        return R.ok().

    put("msgDetials","文件生成成功！").

    put("SOURCE_CODE",content.toString());
}
}
