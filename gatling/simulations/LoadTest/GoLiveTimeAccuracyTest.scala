
import scala.concurrent.duration._
import java.util.concurrent.TimeUnit

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._


class GoLiveTimeAccuracyTest {

	/*
	To test accuracy of publish go live time
	1) Start Load Test on one terminal window (set test duration to around 10 minutes)
	   This provides the background traffic to mimic busy server load.
	2) On the CMS, schedule a publication to publish within that 10 minute window
	   This publication should be referenced in UPCOMING_PUBLICATION_PATH
	3) Start PublishAccuracyTest in separate terminal window
	4) As Gatling tries to send request to UPCOMING_PUBLICATION_PATH, the console
	   logs should show lots of warnings for http response 404 (document not found)
	5) Once publish time passes, http response code will change to 200 (ok)
	6) Review the console to see exactly when publish took place (i.e. when the
	   http status changes from 404 to 200)
	*/
	val UPCOMING_PUBLICATION_PATH = "/publications/ci-hub-old/publishtime-test-document-to-delete-after"

	val scn = scenario("GoLiveTimeAccuracyTest")
	  	.exec(http("Publication")
		  	.get(UPCOMING_PUBLICATION_PATH)
	  	  	.check(status.is(700))	// just an arbitrary value to ensure it keeps being
									// retried even when a 200 response is received.
		)
}
