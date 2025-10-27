# negativenumbers
Grails 7.0.0 application with domain containing long and bigdecimal fields for demonstrating bug in edit, update and save negative numbers when browser has locale set to nb-NO.

log-different-combinations-result.txt contains the results of the different combinations of locale and browser.

Problem description for negative numbers:
* Long fields is not prefilled with negative numbers
* I have not been able to find a way to enter negative numbers in Long fields
* BigDecimal fields is prefilled with negative numbers
* I have been able to find a way to enter negative numbers in BigDecimal fields using typographic minus sign. This sign is not readily available in the keyboard.