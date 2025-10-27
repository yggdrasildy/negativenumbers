package com.test

import geb.spock.GebSpec
import grails.testing.mixin.integration.Integration

@Integration
class ItemGebSpec extends GebSpec {

    def "open and try to save item #name -> success: #expectedSuccess"() {
        when: 'Navigate to the Item list'
        go "/item/index"
        waitFor { $("#list-") }

        and: 'Open the item details from the list by its name'
        def itemLink = $("#list- a", text: name).first()
        assert itemLink: "Did not find link for item '${name}' on the index page"
        itemLink.click()

        then: 'We are on the show page for that item'
        waitFor { browser.currentUrl =~ /\/(item)\/(show)\/(\d+)/ }

        when: 'Open the Edit form'
        def editLink = $("a[href*='/item/edit/']")
        assert editLink: "Did not find Edit link on the show page for '${name}'"
        editLink.click()

        then: 'The edit form is displayed'
        waitFor { $("#edit-item").displayed }

        when: 'Submit the form without changing values (this is what exposes the bug)'
        $("#edit-item button[type='submit']").click()

        then: 'Validate the expected outcome for this item'
        if (expectedSuccess) {
            // Success: redirect back to the show page and no validation errors
            waitFor { browser.currentUrl =~ /\/(item)\/(show)\/(\d+)/ }
            assert $("ul.alert.alert-danger").empty: "Unexpected validation errors shown when saving '${name}'"
        } else {
            // Failure: still on edit page with validation error list visible
            waitFor { $("#edit-item").displayed }
//            assert !$("ul.alert.alert-danger").empty: "Expected validation errors were NOT shown when saving '${name}'"
            assert $("#edit-item").displayed: 'Expected to be on edit page'
        }

        where:
        name         | expectedSuccess
        'Product A'  | true
        'Product B'  | false
        'Product C'  | true
    }

    def 'create a new product with negative numbers'() {
        when: 'Open for create'
        go "/item/index"
        then: 'We are on the create page for that item'
        waitFor { browser.currentUrl =~ /\/(item)\/(crete)/ }


    }
}
