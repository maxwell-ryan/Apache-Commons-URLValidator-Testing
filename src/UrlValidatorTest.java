//package URLValidatorInCorrect.bin;

/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import junit.framework.TestCase;


/**
 * Performs Validation Test for url validations.
 *
 * @version $Revision: 1128446 $ $Date: 2011-05-27 13:29:27 -0700 (Fri, 27 May 2011) $
 */
public class UrlValidatorTest extends TestCase {

   private boolean printStatus = false;
   private boolean printIndex = false;//print index that indicates current scheme,host,port,path, query test were using.

   public UrlValidatorTest(String testName) {
      super(testName);
   }

   
   
   public void testManualTest() {
	   UrlValidator urlVal = new UrlValidator(null, null, UrlValidator.ALLOW_ALL_SCHEMES);
	   System.out.println(urlVal.isValid("http://www.amazon.com"));
	   
	   
   }
   
   /*url is composed for following components:
   1) application layer protocol (http, ftp...)
   2) hostname (google, espn, www.wallstreetjournal, engr.oregonstate, 156.34.28.112)
   3) top level domain (aka. COM, EDU, NET)
   4) path (/word, /word/anotherword, /chars/chars/chars)
   
   
   */
   public void testYourFirstPartition() {
	   
	   System.out.println("Test - Partition 1 - URL protocol");
	   
	   //track if any at least one failure found in entire partition set test
	   boolean protocolOverallResult;
	   
	   //construct array of 'good' protocol partition inputs
	   ResultPair[] validProtocolTestInputs = {
			   new ResultPair("http", true),
			   new ResultPair("ftp", true),
			   new ResultPair("https", true),
			   new ResultPair("ftps", true)
	   };
	   
	   //construct array of 'bad' protocol partition inputs
	   ResultPair[] invalidProtocolTestInputs = {
			   new ResultPair("ftps", false),
			   new ResultPair("htp", false),
			   new ResultPair("htt", false),
			   new ResultPair("httpp", false),
			   new ResultPair("htttp", false),
			   new ResultPair("ttp", false),
			   new ResultPair("", false),
			   new ResultPair(" ", false),
	   };
	   
	   String[] validProtocols = {"http", "https", "ftp", "ftps"};
	   String[] invalidProtocols = {"htt", "htts", "ft"};
	   
	   boolean protocolTestResult;
	   UrlValidator testURLValidator = new UrlValidator(validProtocols);
	   
	   //iterate over all good partition inputs and ensure that PUT agrees
	   for (int x = 0; x < validProtocolTestInputs.length; x++) {
		   ResultPair currentResultPair = validProtocolTestInputs[x];
		  
		   protocolTestResult = testURLValidator.isValidScheme(currentResultPair.item);
		   
		   if (protocolTestResult == currentResultPair.valid) {
			   System.out.println("PASS: Protocol " + currentResultPair.item + " correctly determined to be: " + String.valueOf(currentResultPair.valid));
		   } else {
			   System.out.println("FAILURE: Protocol " + currentResultPair.item + " incorrectly determined to be: " + String.valueOf(currentResultPair.valid));
			   protocolOverallResult = false;
		   }
	   }
	   
	   //iterate over all bad partition inputs and ensure that PUT agrees
	   for (int x = 0; x < invalidProtocolTestInputs.length; x++) {
		   ResultPair currentResultPair = invalidProtocolTestInputs[x];
		  
		   protocolTestResult = testURLValidator.isValidScheme(currentResultPair.item);
		   
		   if (protocolTestResult == currentResultPair.valid) {
			   System.out.println("PASS: Protocol " + currentResultPair.item + " correctly determined to be: " + String.valueOf(currentResultPair.valid));
		   } else {
			   System.out.println("FAILURE: Protocol " + currentResultPair.item + " incorrectly determined to be: " + String.valueOf(currentResultPair.valid));
			   protocolOverallResult = false;
		   }
	   }
   }
   
   public void testYourSecondPartition() {
	   
	   System.out.println("Test - Partition 2 - hostname/authority");
	   
	   //track if any at least one failure found in entire partition set test
	   boolean hostnameOverallResult;
	   
	   //construct array of 'good' authority partition inputs
	   ResultPair[] validHostnameTestInputs = {
			   new ResultPair("www.espn.com", true),
			   new ResultPair("espn.com", true),
			   new ResultPair("google.com", true),
			   new ResultPair("oregonstate.edu", true),
			   new ResultPair("microsoft.net", true),
			   new ResultPair("theverge.com:80", true),
			   new ResultPair("151.101.1.52", true),
	   };
	   
	   //construct array of 'bad' authority partition inputs
	   ResultPair[] invalidHostnameTestInputs = {
			   new ResultPair("www.espn.co", false),
			   new ResultPair(".com", false),
			   new ResultPair("google.com", false),
			   new ResultPair("oregonstate.edu", false),
			   new ResultPair("microsoft.net", false),
			   new ResultPair("theverge.com:::80", false),
			   new ResultPair("151.101.1.52.6", false),
			   new ResultPair("151.101.1", false),
			   new ResultPair("151.260.1.52", false)
	   };
	   
	   boolean hostnameTestResult;
	   UrlValidator testURLValidator = new UrlValidator();
	   
	   //iterate over all good partition inputs and ensure that PUT agrees
	   for (int x = 0; x < validHostnameTestInputs.length; x++) {
		   ResultPair currentResultPair = validHostnameTestInputs[x];
		  
		   hostnameTestResult = testURLValidator.isValidAuthority(currentResultPair.item);
		   
		   if (hostnameTestResult == currentResultPair.valid) {
			   System.out.println("PASS: Authority " + currentResultPair.item + " correctly determined to be: " + String.valueOf(currentResultPair.valid));
		   } else {
			   System.out.println("Failure: Authority " + currentResultPair.item + " incorrectly determined to be: " + String.valueOf(currentResultPair.valid));
			   hostnameOverallResult = false;
		   }
	   }
	   
	   //iterate over all bad partition inputs and ensure that PUT agrees
	   for (int x = 0; x < invalidHostnameTestInputs.length; x++) {
		   ResultPair currentResultPair = invalidHostnameTestInputs[x];
		  
		   hostnameTestResult = testURLValidator.isValidAuthority(currentResultPair.item);
		   
		   if (hostnameTestResult == currentResultPair.valid) {
			   System.out.println("PASS: Authority " + currentResultPair.item + " correctly determined to be: " + String.valueOf(currentResultPair.valid));
		   } else {
			   System.out.println("Failure: Authority " + currentResultPair.item + " incorrectly determined to be: " + String.valueOf(currentResultPair.valid));
			   hostnameOverallResult = false;
		   }
	   }
   }
   
   public void testYourThirdPartition() {
	   
	   System.out.println("Test - Partition 3 - Path");
	   
	   //track if any at least one failure found in entire partition set test
	   boolean pathOverallResult;
	   
	   //construct array of 'good' path partition inputs
	   ResultPair[] validPathTestInputs = {
			   new ResultPair("/test/the/path", true),
			   new ResultPair("/test/the/path/", true),
			   new ResultPair("/", false),
			   new ResultPair("/test", false),
			   new ResultPair("/test/", true),
			   new ResultPair("/12/123/1234", true),
			   new ResultPair("/@/", true)
	   };
	   
	   //construct array of 'bad' path partition inputs
	   ResultPair[] invalidPathTestInputs = {
			   new ResultPair("/test\the/path", false),
			   new ResultPair("\test/the/path/", false),
			   new ResultPair("//", false),
			   new ResultPair("t/est", false),
			   new ResultPair("/tes/t", false),
			   new ResultPair("121231234", false),
			   new ResultPair("", false)
	   };
	   
	   boolean pathTestResult;
	   UrlValidator testURLValidator = new UrlValidator();
	   
	   //iterate over all good partition inputs and ensure that PUT agrees
	   for (int x = 0; x < validPathTestInputs.length; x++) {
		   ResultPair currentResultPair = validPathTestInputs[x];
		  
		   pathTestResult = testURLValidator.isValidPath(currentResultPair.item);
		   
		   if (pathTestResult == currentResultPair.valid) {
			   System.out.println("PASS: Path " + currentResultPair.item + " correctly determined to be: " + String.valueOf(currentResultPair.valid));
		   } else {
			   System.out.println("FAILURE: Path " + currentResultPair.item + " incorrectly determined to be: " + String.valueOf(currentResultPair.valid));
			   pathOverallResult = false;
		   }
	   }
	   
	   //iterate over all bad partition inputs and ensure that PUT agrees
	   for (int x = 0; x < invalidPathTestInputs.length; x++) {
		   ResultPair currentResultPair = invalidPathTestInputs[x];
		  
		   pathTestResult = testURLValidator.isValidPath(currentResultPair.item);
		   
		   if (pathTestResult == currentResultPair.valid) {
			   System.out.println("PASS: Path " + currentResultPair.item + " correctly determined to be: " + String.valueOf(currentResultPair.valid));
		   } else {
			   System.out.println("FAILURE: Path " + currentResultPair.item + " incorrectly determined to be: " + String.valueOf(currentResultPair.valid));
			   pathOverallResult = false;
		   }
	   }
   }
   
   
   
   public void testIsValid() {
	   for(int i = 0;i<10000;i++)
	   {
		   
	   }
   }
   
   public void testAnyOtherUnitTest() {
	   
   }
   /**
    * Create set of tests by taking the testUrlXXX arrays and
    * running through all possible permutations of their combinations.
    *
    * @param testObjects Used to create a url.
    */
   

}