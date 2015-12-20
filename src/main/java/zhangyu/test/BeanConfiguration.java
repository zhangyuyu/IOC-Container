package zhangyu.test;

import zhangyu.annotation.Bean;
import zhangyu.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean(name = "dog")
    public Animal dog() {
        return new Dog();
    }

}
