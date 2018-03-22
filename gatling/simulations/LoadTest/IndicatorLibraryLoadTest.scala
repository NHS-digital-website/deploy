
import java.util.concurrent.TimeUnit
import scala.concurrent.duration._
import scala.util.Random

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class IndicatorLibraryLoadTest {

	/**
	x1 /publications
		x2 series document (24)
		x4 publication document (48)
		x8 dataset document (100)
	  */

    var indicators = List(
        "/data-and-information/national-indicator-library/sample-indicator/sample-indicator",
        "/data-and-information/national-indicator-library/acceptance-tests/bare-minimum-indicator/bare-minimum-indicator",
        "/data-and-information/national-indicator-library/acceptance-tests/national-indicator/national-indicator",
        "/data-and-information/national-indicator-library/acceptance-tests/search-test-definition-indicator/search-test-definition-indicator",
        "/data-and-information/national-indicator-library/acceptance-tests/search-test-title-indicator/search-test-title-indicator",
        "/data-and-information/national-indicator-library/acceptance-tests/unassured-indicator/unassured-indicator"
    )
	var uatIndicators = List(
		"/data-and-information/national-indicator-library/completion-of-cardiac-rehabilitation-following-an-admission-for-coronary-heart-disease/completion-of-cardiac-rehabilitation-following-an-admission-for-coronary-heart-disease",
        "/data-and-information/national-indicator-library/neonatal-mortality-and-stillbirths/neonatal-mortality-and-stillbirths",
        "/data-and-information/national-indicator-library/cancers-detected-at-stage-1-or-2/cancers-detected-at-stage-1-or-2",
        "/data-and-information/national-indicator-library/people-with-stroke-admitted-to-an-acute-stroke-unit-within-4-hours-of-arrival-at-hospital/people-with-stroke-admitted-to-an-acute-stroke-unit-within-4-hours-of-arrival-at-hospital",
        "/data-and-information/national-indicator-library/total-health-gain-as-assessed-by-patients-for-elective-procedures-retired-as-of-may-15/total-health-gain-as-assessed-by-patients-for-elective-procedures-retired-as-of-may-15",
        "/data-and-information/national-indicator-library/incidence-of-healthcare-associated-infection-hcai-c-difficile-infection/incidence-of-healthcare-associated-infection-hcai-c-difficile-infection",
        "/data-and-information/national-indicator-library/hip-fracture-proportion-of-patients-recovering-to-their-previous-levels-of-mobility-walking-ability-at-120-days/hip-fracture-proportion-of-patients-recovering-to-their-previous-levels-of-mobility-walking-ability-at-120-days",
        "/data-and-information/national-indicator-library/stroke-patients-who-have-a-joint-health-and-social-care-plan-on-discharge-from-hospital/stroke-patients-who-have-a-joint-health-and-social-care-plan-on-discharge-from-hospital",
        "/data-and-information/national-indicator-library/deaths-from-venous-thromboembolism-vte-related-events-90-days-post-discharge-from-hospital/deaths-from-venous-thromboembolism-vte-related-events-90-days-post-discharge-from-hospital",
        "/data-and-information/national-indicator-library/low-birth-weight-term-babies/low-birth-weight-term-babies",
        "/data-and-information/national-indicator-library/alcohol-specific-hospital-admissions/alcohol-specific-hospital-admissions",
        "/data-and-information/national-indicator-library/hip-fracture-multifactorial-risk-assessment/hip-fracture-multifactorial-risk-assessment",
        "/data-and-information/national-indicator-library/maternal-smoking-at-delivery/maternal-smoking-at-delivery",
        "/data-and-information/national-indicator-library/people-who-have-had-an-acute-stroke-who-spend-90-or-more-of-their-stay-on-a-stroke-unit/people-who-have-had-an-acute-stroke-who-spend-90-or-more-of-their-stay-on-a-stroke-unit",
        "/data-and-information/national-indicator-library/health-inequality-area-deprivation-health-related-quality-of-life-for-people-with-long-term-conditions/health-inequality-area-deprivation-health-related-quality-of-life-for-people-with-long-term-conditions",
        "/data-and-information/national-indicator-library/of-people-with-hip-fracture-the-proportion-who-receive-surgery-on-the-day-of-or-the-day-after-admission/of-people-with-hip-fracture-the-proportion-who-receive-surgery-on-the-day-of-or-the-day-after-admission",
        "/data-and-information/national-indicator-library/referrals-to-cardiac-rehabilitation-within-5-days-of-an-admission-for-coronary-heart-disease/referrals-to-cardiac-rehabilitation-within-5-days-of-an-admission-for-coronary-heart-disease",
        "/data-and-information/national-indicator-library/incidence-of-healthcare-associated-infection-hcai-methicillin-resistant-staphylococcus-aureus-mrsa/incidence-of-healthcare-associated-infection-hcai-methicillin-resistant-staphylococcus-aureus-mrsa",
        "/data-and-information/national-indicator-library/thirty-day-mortality-by-trust-comparing-patients-admitted-on-saturday-and-sunday-to-patients-admitted-tuesday-wednesday-and-thursday/thirty-day-mortality-by-trust-comparing-patients-admitted-on-saturday-and-sunday-to-patients-admitted-tuesday-wednesday-and-thursday",
        "/data-and-information/national-indicator-library/people-with-serious-mental-illness-smi-who-have-received-complete-list-of-physical-checks/people-with-serious-mental-illness-smi-who-have-received-complete-list-of-physical-checks",
        "/data-and-information/national-indicator-library/unplanned-readmissions-to-mental-health-services-within-30-days-of-a-mental-health-inpatient-discharge-in-people-aged-17-and-over/unplanned-readmissions-to-mental-health-services-within-30-days-of-a-mental-health-inpatient-discharge-in-people-aged-17-and-over",
        "/data-and-information/national-indicator-library/under-75-mortality-from-liver-disease/under-75-mortality-from-liver-disease",
        "/data-and-information/national-indicator-library/dementia-65-estimated-diagnosis-rate/dementia-65-estimated-diagnosis-rate",
        "/data-and-information/national-indicator-library/proportion-of-people-feeling-supported-to-manage-their-conditions/proportion-of-people-feeling-supported-to-manage-their-conditions",
        "/data-and-information/national-indicator-library/smoking-rates-in-people-with-serious-mental-illness/smoking-rates-in-people-with-serious-mental-illness",
        "/data-and-information/national-indicator-library/cancer-stage-at-diagnosis/cancer-stage-at-diagnosis",
        "/data-and-information/national-indicator-library/hip-fracture-formal-hip-fracture-programme/hip-fracture-formal-hip-fracture-programme",
        "/data-and-information/national-indicator-library/hip-fracture-proportion-of-patients-recovering-to-their-previous-levels-of-mobility-walking-ability-at-30-days/hip-fracture-proportion-of-patients-recovering-to-their-previous-levels-of-mobility-walking-ability-at-30-days",
        "/data-and-information/national-indicator-library/alcohol-specific-readmission-to-any-hospital-within-30-days-after-the-last-previous-discharge-following-an-alcohol-specific-admission/alcohol-specific-readmission-to-any-hospital-within-30-days-after-the-last-previous-discharge-following-an-alcohol-specific-admission",
        "/data-and-information/national-indicator-library/health-inequalities-area-deprivation-life-expectancy-at-75/health-inequalities-area-deprivation-life-expectancy-at-75",
        "/data-and-information/national-indicator-library/mortality-from-breast-cancer-in-females/mortality-from-breast-cancer-in-females",
        "/data-and-information/national-indicator-library/mortality-within-30-days-of-hospital-admission-for-stroke/mortality-within-30-days-of-hospital-admission-for-stroke",
        "/data-and-information/national-indicator-library/tooth-extractions-for-children-admitted-as-inpatients-to-hospital-aged-10-years-and-under/tooth-extractions-for-children-admitted-as-inpatients-to-hospital-aged-10-years-and-under",
        "/data-and-information/national-indicator-library/record-of-lung-cancer-at-decision-to-treat/record-of-lung-cancer-at-decision-to-treat",
        "/data-and-information/national-indicator-library/people-who-have-a-follow-up-assessment-between-4-and-8-months-after-initial-admission-for-stroke/people-who-have-a-follow-up-assessment-between-4-and-8-months-after-initial-admission-for-stroke",
        "/data-and-information/national-indicator-library/percentage-of-cancer-diagnoses-via-emergency-routes/percentage-of-cancer-diagnoses-via-emergency-routes",
        "/data-and-information/national-indicator-library/all-cause-mortality-12-months-following-a-first-emergency-admission-to-hospital-for-heart-failure-in-people-aged-16-and-over/all-cause-mortality-12-months-following-a-first-emergency-admission-to-hospital-for-heart-failure-in-people-aged-16-and-over",
        "/data-and-information/national-indicator-library/emergency-hospital-admissions-for-hip-fracture-in-people-aged-60-and-over/emergency-hospital-admissions-for-hip-fracture-in-people-aged-60-and-over"
	)

	var executions = Seq(
		exec(http("/data-and-information/national-indicator-library").get("/"))
	)

    indicators.zipWithIndex.foreach { case(el, i) => {
        executions = executions :+ exec( http(s"indicators document #$i").get(el) )
    } }

    // uatIndicators.zipWithIndex.foreach { case(el, i) => {
    //     executions = executions :+ exec( http(s"indicators document #$i").get(el) )
    // } }

	val scn = scenario("IndicatorLibraryLoadTest").exec(Random.shuffle(executions))
}
