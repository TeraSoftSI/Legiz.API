Feature: LawyerAdding
  As a Developer
  I want to add Lawyer through API
  So that It can be available to applications

  Background:
    Given The Endpoint "http://localhost:%d/api/v1/lawyers" is available

    @lawyer-adding
    Scenario: Add Lawyer
      When A Lawyer Request is sent with values "g-guci123", "seba123", "Gustavo"
      Then A Response with Status 200 is received
      And A Lawyer Resource with values "g-guci123", "seba123", "Gustavo" is included in Response Body
