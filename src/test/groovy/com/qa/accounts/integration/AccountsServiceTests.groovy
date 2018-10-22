package com.qa.accounts.integration

import org.junit.Rule
import org.junit.rules.ExpectedException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import spock.lang.Specification
import spock.lang.Stepwise
import spock.lang.Unroll

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get

@SpringBootTest
@AutoConfigureMockMvc
@Stepwise
class AccountsServiceTests extends Specification {

    @Autowired
    private MockMvc mvc

    @Rule
    public ExpectedException thrown = ExpectedException.none()

    def 'POST and verify response'() {
        when:
        MvcResult mvcPOSTResult = mvc.perform(postReqBuilder(inputJSON)).andReturn()

        String resultJSON = mvcPOSTResult.response.contentAsString

        then:
        mvcPOSTResult.response.status == HttpStatus.OK.value()
        resultJSON.contains(firstNameAsString)
        resultJSON.contains(secondNameAsString)
        resultJSON.contains(accountNumber.toString())

        where:
        inputJSON                                                             | firstNameAsString  | secondNameAsString | accountNumber
        '''{"firstName": "JOHN","secondName": "DOE","accountNumber": 1234}''' | "firstName='JOHN'" | "secondName='DOE'" | 1234

    }

    @Unroll
    def 'DELETE id=#id with status=#status and message=#msg'() {

        when:
        MvcResult mvcPOSTSecondResult = mvc.perform(delete("/accounts/${id}")).andReturn()

        then:
        mvcPOSTSecondResult.response.status == status
        mvcPOSTSecondResult.response.contentAsString.contains(msg)

        where:
        id       || status                         || msg
        1        || HttpStatus.OK.value()          || 'account has been successfully deleted'
        1        || HttpStatus.NOT_FOUND.value()   || 'No class com.qa.accounts.domain.Account entity with id 1 exists!'
        2        || HttpStatus.NOT_FOUND.value()   || 'No class com.qa.accounts.domain.Account entity with id 2 exists!'
        'string' || HttpStatus.BAD_REQUEST.value() || ''

    }

    @Unroll
    def 'POST and verify GET results for #accountNumber'() {

        when:
        mvc.perform(postReqBuilder(inputJSON)).andReturn()

        MvcResult mvcGETResult = mvc.perform(get("/accounts")).andReturn()
        String resultJSON = mvcGETResult.response.contentAsString

        then:
        mvcGETResult.response.status == HttpStatus.OK.value()
        println(" after executing GET: $resultJSON")

        where:
        inputJSON                                                               | firstNameAsString  | secondNameAsString    | accountNumber
        '''{"firstName": "JOHN","secondName": "DOE","accountNumber": 1234}'''   | "firstName='JOHN'" | "secondName='DOE'"    | 1234
        '''{"firstName": "JANE","secondName": "DOE","accountNumber": 1235}'''   | "firstName='JANE'" | "secondName='DOE'"    | 1235
        '''{"firstName": "JIM","secondName": "TAYLOR","accountNumber": 1236}''' | "firstName='JIM'"  | "secondName='TAYLOR'" | 1236

    }

    def 'POST with duplicate input gives 404 response'() {

        when:
        MvcResult mvcPOSTSecondResult = mvc.perform(postReqBuilder(requestJSON)).andReturn()

        then:
        mvcPOSTSecondResult.response.status == HttpStatus.BAD_REQUEST.value()
        mvcPOSTSecondResult.response.contentAsString.contains('could not execute statement')

        where:
        requestJSON << ['''{"firstName": "JOHN","secondName": "DOE","accountNumber": 1234}''']

    }

    @Unroll
    def 'POST with #missingField missing gives 404 response'() {

        when:
        MvcResult mvcPOSTSecondResult = mvc.perform(postReqBuilder(requestJSON)).andReturn()

        then:
        mvcPOSTSecondResult.response.status == HttpStatus.BAD_REQUEST.value()
        mvcPOSTSecondResult.response.contentAsString.contains(errorMsg)

        where:
        requestJSON                                        | missingField    | errorMsg
        '''{"secondName": "DOE","accountNumber": 1234}'''  | 'firstName'     | 'firstName is required field'
        '''{"firstName": "JOHN", "accountNumber": 1234}''' | 'secondName'    | 'secondName is required field'
        '''{"firstName": "JOHN", "secondName": "DOE"}'''   | 'accountNumber' | 'positive integer should be supplied for accountNumber'

    }

    @Unroll
    def 'POST with invalid accountNumber gives 404 response'() {

        when:
        MvcResult mvcPOSTSecondResult = mvc.perform(postReqBuilder(requestJSON)).andReturn()

        then:
        mvcPOSTSecondResult.response.status == HttpStatus.BAD_REQUEST.value()
        mvcPOSTSecondResult.response.contentAsString.contains(errorMsg)

        where:
        requestJSON                                                             | errorMsg
        '''{"firstName": "JOHN", "secondName": "DOE","accountNumber": 0}'''     | 'positive integer should be supplied for accountNumber'
        '''{"firstName": "JOHN", "secondName": "DOE","accountNumber": -1234}''' | 'positive integer should be supplied for accountNumber'

    }

    private static MockHttpServletRequestBuilder postReqBuilder(String inputJSON) {
        MockMvcRequestBuilders
                .post("/accounts")
                .accept(MediaType.APPLICATION_JSON).content(inputJSON)
                .contentType(MediaType.APPLICATION_JSON)
    }

}
