package com.pnt.context.config;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 * @author ERP 시스템즈 이주용
 * @since 2015.04.18
 * @version 1.0
 * @see
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 * 
 *     수정일         수정자               수정내용
 *  --------------    ------------    ---------------------------
 *  2015.04.18        이주용              최초 생성
 * </pre>
 */

public class MybatisUtils {
    private String configPath = "configs/framework/mybatis/mybatis-config.xml";
    private SqlSessionFactory sqlSession;

    MybatisUtils() {
        Reader reader;
        try {
            File resource = new File(this.getConfigPath());
            reader = new FileReader(resource);
            this.sqlSession = new SqlSessionFactoryBuilder().build(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    SqlSessionFactory getSqlSessionFactory() {
        return this.sqlSession;
    }

    void setConfigPath(String configPath) {
        this.configPath = configPath;
    }
    
    private String getConfigPath() {
        return Configurator.getInstance().getServletContext().getRealPath("WEB-INF") + File.separator + this.configPath;
    }
}
