package org.zywx.wbpalmstar.plugin.uexzxing.client.result;


import java.util.Vector;

import org.zywx.wbpalmstar.plugin.uexzxing.Result;


final class BizcardResultParser extends AbstractDoCoMoResultParser {

 
  public static AddressBookParsedResult parse(Result result) {
    String rawText = result.getText();
    if (rawText == null || !rawText.startsWith("BIZCARD:")) {
      return null;
    }
    String firstName = matchSingleDoCoMoPrefixedField("N:", rawText, true);
    String lastName = matchSingleDoCoMoPrefixedField("X:", rawText, true);
    String fullName = buildName(firstName, lastName);
    String title = matchSingleDoCoMoPrefixedField("T:", rawText, true);
    String org = matchSingleDoCoMoPrefixedField("C:", rawText, true);
    String[] addresses = matchDoCoMoPrefixedField("A:", rawText, true);
    String phoneNumber1 = matchSingleDoCoMoPrefixedField("B:", rawText, true);
    String phoneNumber2 = matchSingleDoCoMoPrefixedField("M:", rawText, true);
    String phoneNumber3 = matchSingleDoCoMoPrefixedField("F:", rawText, true);
    String email = matchSingleDoCoMoPrefixedField("E:", rawText, true);

    return new AddressBookParsedResult(maybeWrap(fullName),
                                       null,
                                       buildPhoneNumbers(phoneNumber1, phoneNumber2, phoneNumber3),
                                       maybeWrap(email),
                                       null,
                                       addresses,
                                       org,
                                       null,
                                       title,
                                       null);
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
private static String[] buildPhoneNumbers(String number1, String number2, String number3) {
    Vector numbers = new Vector(3);
    if (number1 != null) {
      numbers.addElement(number1);
    }
    if (number2 != null) {
      numbers.addElement(number2);
    }
    if (number3 != null) {
      numbers.addElement(number3);
    }
    int size = numbers.size();
    if (size == 0) {
      return null;
    }
    String[] result = new String[size];
    for (int i = 0; i < size; i++) {
      result[i] = (String) numbers.elementAt(i);
    }
    return result;
  }

  private static String buildName(String firstName, String lastName) {
    if (firstName == null) {
      return lastName;
    } else {
      return lastName == null ? firstName : firstName + ' ' + lastName;
    }
  }

}
