package com.uav.ops;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author frp
 */
@EnableSwagger2
@SpringBootApplication
@EnableConfigurationProperties
@EnableScheduling
@MapperScan("com/uav/ops/mapper")
@EntityScan("com/uav/ops/entity")
@EnableAsync
@ServletComponentScan("com.uav.ops.config.filter")
public class UavOpsApplication {

	public static void main(String[] args) {
		SpringApplication.run(UavOpsApplication.class, args);
	}

}
