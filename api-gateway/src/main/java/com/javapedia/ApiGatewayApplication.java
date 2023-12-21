package com.javapedia;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;


@SpringBootApplication
@EnableDiscoveryClient
public class ApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

//    @Autowired
//    RouteDefinitionLocator locator;

//	@Bean
//	public List<GroupedOpenApi> apis() {
//		List<GroupedOpenApi> groups = new ArrayList<>();
//		List<RouteDefinition> definitions = locator.getRouteDefinitions().collectList().block();
//		assert definitions != null;
//		definitions.stream().filter(routeDefinition -> routeDefinition.getId().matches(".*-service")).forEach(routeDefinition -> {
//			String name = routeDefinition.getId().replaceAll("-service", "");
//			groups.add(GroupedOpenApi.builder().pathsToMatch("/" + name + "/**").group(name).build());
//		});
//		return groups;
//	}


//    @Bean
//    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
//        return builder.routes()
//                .route("r1", r -> r.host("**-service")
//                        .and()
//                        .path("/api/orders")
//                        .uri("http://baeldung.com"))
//
//                .build();
//    }
}
