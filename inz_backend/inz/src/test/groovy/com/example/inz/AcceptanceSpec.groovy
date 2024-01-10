package com.example.inz

import com.example.inz.category.CategoryController
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class AcceptanceSpec extends Specification {

    @Autowired
    private CategoryController webController

    def "when context is loaded then all expected beans are created"() {
        expect: "the WebController is created"
        webController
    }
}
