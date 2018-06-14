package com.cimr.config;

import java.io.File;
import java.io.IOException;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
@Configuration  
public class HttpsConfig {
	
	  @Value("${http.port}")
	    private Integer port;

//	    @Value("${https.ssl.key-store-password}")
//	    private String key_store_password;


	    
    @Bean
    public EmbeddedServletContainerFactory servletContainer() {
        TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory();
        tomcat.addAdditionalTomcatConnectors(createSslConnector()); // 添加http
        return tomcat;
    }
    
	// 配置http
  private Connector createSslConnector() {
	  Connector connector=new Connector("org.apache.coyote.http11.Http11NioProtocol");
      connector.setScheme("http");
      connector.setPort(port);
      connector.setSecure(false);
      
      return connector;
  }
	
//	// 配置https
//    private Connector createSslConnector() {
//        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
//        Http11NioProtocol protocol = (Http11NioProtocol) connector.getProtocolHandler();
//        try {
////        	getClass().getResource("yxtest.club.jks").getFile();
//            File keystore = new ClassPathResource("yxtest.club.jks").getFile();
//            /*File truststore = new ClassPathResource("sample.jks").getFile();*/
//            connector.setScheme("https");
//            connector.setSecure(true);
//            connector.setPort(port);
//            protocol.setSSLEnabled(true);
//            protocol.setKeystoreFile(keystore.getAbsolutePath());
//            protocol.setKeystorePass(key_store_password);
////            protocol.setKeyPass(key_password);
//            return connector;
//        }
//        catch (Exception ex) {
//            throw new IllegalStateException("can't access keystore: [" + "keystore"
//                    + "] or truststore: [" + "keystore" + "]", ex);
//        }
//    }
}
