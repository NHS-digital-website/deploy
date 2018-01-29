
import java.util.concurrent.TimeUnit
import scala.concurrent.duration._
import scala.util.Random

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class PublicationSystemLoadTest {

	/**
	x1 /publications
		x2 series document (24)
		x4 publication document (48)
		x8 dataset document (100)
	  */
	var series = List(
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-public-health/archive/circulatory-diseases",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-public-health/archive/dental-health",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-hospital-care/current",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-public-health/archive/alcohol-consumption",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/archive/mortality-from-breast-cancer",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-public-health/archive/general-health",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-public-health/archive/chromosomal-abormalities-congential-malformations",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/archive/mortality-from-land-transport-accidents",
		"/publications/clinical-indicators/adult-social-care-outcomes-framework-ascof/archive",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/archive/deaths-at-home",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-public-health/archive/obesity-nutrition",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-other/archive/area-classification-including-deprivation",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/archive/mortality-from-chronic-liver-disease",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-local-basket-of-inequality-indicators-lboi/archive/local-basket-of-inequality-indicators-lboi",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/archive/mortality-from-acute-myocardial-infarction-or-ischeamic-heart-disease-other-than-acute-myocardial-infarction",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-other/archive/education",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-hospital-care/archive/emergency-readmissions-to-hospital-within-28-days-of-discharge",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-other/current",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-public-health/archive/abortions",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-other/archive/social-care",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/archive/mortality-from-accidents",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-public-health/archive/births",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/archive/mortality-from-gastric-duodenal-and-peptic-ulcers",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-hospital-care/archive/hospital-episodes-admissions",
	)
	var publications = List(
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-hospital-care/current/returning-to-usual-place-of-residence",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-public-health/current/obesity-nutrition",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/current/mortality-from-respiratory-diseases",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/current/mortality-from-chronic-rheumatic-heart-disease",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/current/mortality-from-breast-cancer",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-public-health/current/abortions",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/current/mortality-from-stroke",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-hospital-care/current/emergency-admissions",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-public-health/current/respiratory-diseases",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/current/mortality-from-hypertensive-disease",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-public-health/current/circulatory-diseases",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/current/mortality-from-acute-myocardial-infarction-or-ischeamic-heart-disease-other-than-acute-myocardial-infarction",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/current/mortality-from-oesophageal-cancer",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/current/mortality-from-lung-cancer",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/current/mortality-from-fracture-of-femur",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-hospital-care/current/emergency-hospital-admissions-and-timely-surgery",
		"/publications/clinical-indicators/adult-social-care-outcomes-framework-ascof/current",
		"/publications/clinical-indicators/nhs-outcomes-framework/current",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/current/mortality-from-bladder-cancer",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/current/mortality-from-accidental-falls",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/current/mortality-from-diabetes",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-other/current/social-care",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-public-health/current/immunisations-and-infectious-diseases",
		"/publications/clinical-indicators/ccg-outcomes-indicator-set/current"
	)
	var datasets = List(
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/current/mortality-from-infectious-diseases/mortality-from-tuberculosis-crude-death-rate-by-age-group-3-year-average-mfp/mortality-from-tuberculosis-crude-death-rate-by-age-group-3-year-average-mfp",
		"/publications/clinical-indicators/nhs-outcomes-framework/current/domain-2-enhancing-quality-of-life-for-people-with-long-term-conditions-nof/2-4-health-related-quality-of-life-for-carers/2-4-health-related-quality-of-life-for-carers",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-local-basket-of-inequality-indicators-lboi/current/local-basket-of-inequality-indicators-lboi/mortality-from-all-cancer-directly-age-standardised-rate-by-deprivation-quintile-persons-under-75-years/mortality-from-all-cancer-directly-age-standardised-rate-by-deprivation-quintile-persons-under-75-years",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/current/mortality-from-hodgkin-s-disease/mortality-from-hodgkin-s-disease-crude-death-rate-by-age-group-3-year-average-mfp/mortality-from-hodgkin-s-disease-crude-death-rate-by-age-group-3-year-average-mfp",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-hospital-care/current/emergency-readmissions-to-hospital-within-28-days-of-discharge/emergency-readmissions-to-hospital-within-28-days-of-discharge-hysterectomy-indirectly-standardised-percent-all-ages-annual-trend-f/emergency-readmissions-to-hospital-within-28-days-of-discharge-hysterectomy-indirectly-standardised-percent-all-ages-annual-trend-f",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/current/mortality-from-prostate-cancer/mortality-from-prostate-cancer-number-by-age-group-annual-m/mortality-from-prostate-cancer-number-by-age-group-annual-m",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-public-health/current/general-health/self-assessed-general-health-standardised-percent-16-years-3-year-average-trend-mfp/self-assessed-general-health-standardised-percent-16-years-3-year-average-trend-mfp",
		"/publications/clinical-indicators/nhs-outcomes-framework/current/domain-2-enhancing-quality-of-life-for-people-with-long-term-conditions-nof/2-2-employment-of-people-with-long-term-conditions/2-2-employment-of-people-with-long-term-conditions",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/current/mortality-from-stroke/mortality-from-stroke-directly-standardised-rate-75-years-3-year-average-mfp/mortality-from-stroke-directly-standardised-rate-75-years-3-year-average-mfp",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-hospital-care/current/hospital-procedures/hospital-procedures-lower-limb-amputations-in-diabetic-patients-indirectly-standardised-rate-all-ages-annual-trend-f/hospital-procedures-lower-limb-amputations-in-diabetic-patients-indirectly-standardised-rate-all-ages-annual-trend-f",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/current/infant-mortality/stillbirths-crude-rate-annual-p/stillbirths-crude-rate-annual-p",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-hospital-care/current/emergency-readmissions-to-hospital-within-28-days-of-discharge/emergency-readmissions-to-hospital-within-28-days-of-discharge-stroke-indirectly-standardised-percent-all-ages-annual-trend-m/emergency-readmissions-to-hospital-within-28-days-of-discharge-stroke-indirectly-standardised-percent-all-ages-annual-trend-m",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-hospital-care/current/emergency-readmissions-to-hospital-within-28-days-of-discharge/emergency-readmissions-to-hospital-within-28-days-of-discharge-primary-hip-replacement-surgery-indirectly-standardised-percent-all-ages-annual-trend-p/emergency-readmissions-to-hospital-within-28-days-of-discharge-primary-hip-replacement-surgery-indirectly-standardised-percent-all-ages-annual-trend-p",
		"/publications/clinical-indicators/adult-social-care-outcomes-framework-ascof/current/ensuring-people-have-a-positive-experience-of-care-and-support/3a-overall-satisfaction-of-people-who-use-services-with-their-care-and-support/3a-overall-satisfaction-of-people-who-use-services-with-their-care-and-support",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/current/mortality-from-hypertensive-disease/mortality-from-hypertensive-disease-crude-death-rate-by-age-group-3-year-average-mfp/mortality-from-hypertensive-disease-crude-death-rate-by-age-group-3-year-average-mfp",
		"/publications/clinical-indicators/nhs-outcomes-framework/current/domain-1-preventing-people-from-dying-prematurely-nof/1c-neonatal-mortality-and-stillbirths-formerly-indicator-1-6-ii/1c-neonatal-mortality-and-stillbirths-formerly-indicator-1-6-ii",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-local-basket-of-inequality-indicators-lboi/current/local-basket-of-inequality-indicators-lboi/lboi-indicator-12-1-percentage-uptake-of-influenza-immunisation-in-people-aged-over-65/lboi-indicator-12-1-percentage-uptake-of-influenza-immunisation-in-people-aged-over-65",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/current/mortality-from-all-causes/mortality-from-all-causes-indirectly-standardised-ratio-smr-all-ages-3-year-average-mfp/mortality-from-all-causes-indirectly-standardised-ratio-smr-all-ages-3-year-average-mfp",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-public-health/current/alcohol-consumption/alcohol-consumption-prevalence-of-binge-drinking-more-than-8-units-more-than-6-units-standardised-percent-16-years-annual-trend-mfp/alcohol-consumption-prevalence-of-binge-drinking-more-than-8-units-more-than-6-units-standardised-percent-16-years-annual-trend-mfp",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/current/mortality-from-accidental-falls/mortality-from-accidental-falls-directly-standardised-rate-all-ages-3-year-average-mfp/mortality-from-accidental-falls-directly-standardised-rate-all-ages-3-year-average-mfp",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-public-health/current/circulatory-diseases/systolic-blood-pressure-standardised-mean-16-years-annual-trend-mfp/systolic-blood-pressure-standardised-mean-16-years-annual-trend-mfp",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/current/mortality-from-stomach-cancer/mortality-from-stomach-cancer-directly-standardised-rate-all-ages-3-year-average-mfp/mortality-from-stomach-cancer-directly-standardised-rate-all-ages-3-year-average-mfp",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/current/mortality-from-skull-fracture-and-intracranial-injury/mortality-from-skull-fracture-and-intracranial-injury-indirectly-standardised-ratio-smr-1-years-3-year-average-mfp/mortality-from-skull-fracture-and-intracranial-injury-indirectly-standardised-ratio-smr-1-years-3-year-average-mfp",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-other/current/population/estimates-of-resident-population-number-by-age-group-annual-1994-mfp/estimates-of-resident-population-number-by-age-group-annual-1994-mfp",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/current/mortality-from-prostate-cancer/mortality-from-prostate-cancer-indirectly-standardised-ratio-smr-75-years-3-year-average-m/mortality-from-prostate-cancer-indirectly-standardised-ratio-smr-75-years-3-year-average-m",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/current/mortality-from-all-circulatory-diseases/mortality-from-all-circulatory-diseases-directly-standardised-rate-75-years-3-year-average-mfp/mortality-from-all-circulatory-diseases-directly-standardised-rate-75-years-3-year-average-mfp",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/current/mortality-from-stomach-cancer/mortality-from-stomach-cancer-directly-standardised-rate-75-years-3-year-average-mfp/mortality-from-stomach-cancer-directly-standardised-rate-75-years-3-year-average-mfp",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/current/years-of-life-lost/years-of-life-lost-due-to-mortality-from-coronary-heart-disease-crude-rate-1-74-years-3-year-average-mfp/years-of-life-lost-due-to-mortality-from-coronary-heart-disease-crude-rate-1-74-years-3-year-average-mfp",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-hospital-care/current/deaths-within-30-days/deaths-within-30-days-of-a-hospital-procedure-surgery-non-elective-admissions-indirectly-standardised-rate-all-ages-annual-trend-f-m-p/deaths-within-30-days-of-a-hospital-procedure-surgery-non-elective-admissions-indirectly-standardised-rate-all-ages-annual-trend-f-m-p",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/current/mortality-from-accidental-falls/mortality-from-accidental-falls-directly-standardised-rate-75-years-3-year-average-mfp/mortality-from-accidental-falls-directly-standardised-rate-75-years-3-year-average-mfp",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/current/mortality-from-accidents/mortality-from-accidents-directly-standardised-rate-65-years-annual-trend-mfp/mortality-from-accidents-directly-standardised-rate-65-years-annual-trend-mfp",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/current/years-of-life-lost/years-of-life-lost-due-to-mortality-from-cervical-cancer-crude-rate-1-74-years-3-year-average-f/years-of-life-lost-due-to-mortality-from-cervical-cancer-crude-rate-1-74-years-3-year-average-f",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/current/mortality-from-leukaemia/mortality-from-leukaemia-indirectly-standardised-ratio-smr-75-years-3-year-average-mfp/mortality-from-leukaemia-indirectly-standardised-ratio-smr-75-years-3-year-average-mfp",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/current/years-of-life-lost/years-of-life-lost-due-to-mortality-from-epilepsy-directly-standardised-rate-1-74-years-3-year-average-mfp/years-of-life-lost-due-to-mortality-from-epilepsy-directly-standardised-rate-1-74-years-3-year-average-mfp",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/current/mortality-from-chronic-liver-disease/mortality-from-chronic-liver-disease-including-cirrhosis-directly-standardised-rate-75-years-3-year-average-mfp/mortality-from-chronic-liver-disease-including-cirrhosis-directly-standardised-rate-75-years-3-year-average-mfp",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/current/mortality-from-leukaemia/mortality-from-leukaemia-directly-standardised-rate-75-years-3-year-average-mfp/mortality-from-leukaemia-directly-standardised-rate-75-years-3-year-average-mfp",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-local-basket-of-inequality-indicators-lboi/current/local-basket-of-inequality-indicators-lboi/lboi-indicator-3-4-percentage-of-15-year-olds-in-schools-maintained-by-the-local-authorities-gaining-5-or-more-gcses-at-a-c/lboi-indicator-3-4-percentage-of-15-year-olds-in-schools-maintained-by-the-local-authorities-gaining-5-or-more-gcses-at-a-c",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-other/current/population/estimates-of-resident-population-number-by-age-group-annual-2010-mfp/estimates-of-resident-population-number-by-age-group-annual-2010-mfp",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/current/deaths-at-home/deaths-at-home-from-bladder-cancer-indirectly-standardised-rate-all-ages-3-year-average-mfp/deaths-at-home-from-bladder-cancer-indirectly-standardised-rate-all-ages-3-year-average-mfp",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/current/mortality-from-respiratory-diseases/mortality-from-asthma-indirectly-standardised-ratio-smr-all-ages-3-year-average-mfp/mortality-from-asthma-indirectly-standardised-ratio-smr-all-ages-3-year-average-mfp",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-local-basket-of-inequality-indicators-lboi/current/local-basket-of-inequality-indicators-lboi/lboi-indicator-6-1-number-of-recorded-racial-incidents-per-100-0000-population-applies-to-authorities-services-including-schools-and-to-employment-by-the-authority/lboi-indicator-6-1-number-of-recorded-racial-incidents-per-100-0000-population-applies-to-authorities-services-including-schools-and-to-employment-by-the-authority",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/current/mortality-from-infectious-diseases/mortality-from-tuberculosis-number-by-age-group-annual-mfp/mortality-from-tuberculosis-number-by-age-group-annual-mfp",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/current/mortality-from-all-circulatory-diseases/mortality-from-all-circulatory-diseases-directly-standardised-rate-65-74-years-3-year-average-mfp/mortality-from-all-circulatory-diseases-directly-standardised-rate-65-74-years-3-year-average-mfp",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-other/current/population/census-count-of-resident-population-number-by-age-group-mfp/census-count-of-resident-population-number-by-age-group-mfp",
		"/publications/clinical-indicators/nhs-outcomes-framework/current/domain-4-ensuring-that-people-have-a-positive-experience-of-care-nof/4a-i-patient-experience-of-gp-services/4a-i-patient-experience-of-gp-services",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/current/mortality-from-all-cancers/mortality-from-all-cancers-directly-standardised-rate-all-ages-annual-trend-mfp/mortality-from-all-cancers-directly-standardised-rate-all-ages-annual-trend-mfp",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/current/mortality-from-breast-cancer/mortality-from-breast-cancer-directly-standardised-rate-50-69-years-annual-trend-f/mortality-from-breast-cancer-directly-standardised-rate-50-69-years-annual-trend-f",
		"/publications/clinical-indicators/ccg-outcomes-indicator-set/current/domain-1-preventing-people-from-dying-prematurely-ccg/1-22-hip-fracture-incidence/1-22-hip-fracture-incidence",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-public-health/current/smoking/cigarette-smoking-standardised-percent-16-years-annual-trend-mfp/cigarette-smoking-standardised-percent-16-years-annual-trend-mfp",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/current/mortality-from-epilepsy/mortality-from-epilepsy-directly-standardised-rate-75-years-3-year-average-mfp/mortality-from-epilepsy-directly-standardised-rate-75-years-3-year-average-mfp",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/current/mortality-from-all-circulatory-diseases/mortality-from-all-circulatory-diseases-indirectly-standardised-ratio-smr-all-ages-3-year-average-mfp/mortality-from-all-circulatory-diseases-indirectly-standardised-ratio-smr-all-ages-3-year-average-mfp",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/current/mortality-from-all-causes/mortality-from-all-causes-directly-standardised-rate-65-74-years-3-year-average-mfp/mortality-from-all-causes-directly-standardised-rate-65-74-years-3-year-average-mfp",
		"/publications/clinical-indicators/ccg-outcomes-indicator-set/current/domain-3-helping-people-to-recover-from-episodes-of-ill-health-or-following-injury-ccg/3-13-hip-fracture-multifactorial-risk-assessment/3-13-hip-fracture-multifactorial-risk-assessment",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-public-health/current/dental-health/oral-health-in-children-filled-teeth-mean-5-years-annual-p/oral-health-in-children-filled-teeth-mean-5-years-annual-p",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/current/years-of-life-lost/years-of-life-lost-due-to-mortality-from-accidents-directly-standardised-rate-1-74-years-3-year-average-mfp/years-of-life-lost-due-to-mortality-from-accidents-directly-standardised-rate-1-74-years-3-year-average-mfp",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/current/mortality-from-all-cancers/mortality-from-all-cancers-indirectly-standardised-ratio-smr-65-years-3-year-average-mfp/mortality-from-all-cancers-indirectly-standardised-ratio-smr-65-years-3-year-average-mfp",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/current/mortality-from-infectious-diseases/mortality-from-infectious-and-parasitic-disease-number-by-age-group-annual-mfp/mortality-from-infectious-and-parasitic-disease-number-by-age-group-annual-mfp",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/current/years-of-life-lost/years-of-life-lost-due-to-mortality-from-stomach-cancer-directly-standardised-rate-1-74-years-3-year-average-mfp/years-of-life-lost-due-to-mortality-from-stomach-cancer-directly-standardised-rate-1-74-years-3-year-average-mfp",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-public-health/current/dental-health/oral-health-in-children-missing-teeth-mean-12-years-annual-p/oral-health-in-children-missing-teeth-mean-12-years-annual-p",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-local-basket-of-inequality-indicators-lboi/current/local-basket-of-inequality-indicators-lboi/lboi-indicator-4-5-emergency-admissions-for-assault-in-young-people/lboi-indicator-4-5-emergency-admissions-for-assault-in-young-people",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/current/mortality-from-respiratory-diseases/mortality-from-bronchitis-and-emphysema-crude-death-rate-by-age-group-3-year-average-mfp/mortality-from-bronchitis-and-emphysema-crude-death-rate-by-age-group-3-year-average-mfp",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/current/mortality-from-all-circulatory-diseases/mortality-from-all-circulatory-diseases-directly-standardised-rate-all-ages-annual-trend-mfp/mortality-from-all-circulatory-diseases-directly-standardised-rate-all-ages-annual-trend-mfp",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-public-health/current/circulatory-diseases/treated-and-controlled-high-blood-pressure-percent-and-age-standardised-percent-16-years-3-year-average-trend-mfp/treated-and-controlled-high-blood-pressure-percent-and-age-standardised-percent-16-years-3-year-average-trend-mfp",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/current/mortality-from-chronic-rheumatic-heart-disease/mortality-from-chronic-rheumatic-heart-disease-directly-standardised-rate-75-years-3-year-average-mfp/mortality-from-chronic-rheumatic-heart-disease-directly-standardised-rate-75-years-3-year-average-mfp",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/current/mortality-from-cervical-cancer/mortality-from-cervical-cancer-indirectly-standardised-ratio-smr-all-ages-annual-trend-f/mortality-from-cervical-cancer-indirectly-standardised-ratio-smr-all-ages-annual-trend-f",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/current/mortality-from-coronary-heart-disease/mortality-from-coronary-heart-disease-crude-death-rate-by-age-group-3-year-average-mfp/mortality-from-coronary-heart-disease-crude-death-rate-by-age-group-3-year-average-mfp",
		"/publications/clinical-indicators/nhs-outcomes-framework/current/domain-1-preventing-people-from-dying-prematurely-nof/1a-ii-potential-years-of-life-lost-pyll-from-causes-considered-amenable-to-healthcare-children-and-young-people/1a-ii-potential-years-of-life-lost-pyll-from-causes-considered-amenable-to-healthcare-children-and-young-people",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-public-health/current/immunisations-and-infectious-diseases/incidence-of-measles-number-all-ages-annual-p/incidence-of-measles-number-all-ages-annual-p",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/current/deaths-at-home/deaths-at-home-from-all-cancers-percent-all-ages-3-year-average-mfp/deaths-at-home-from-all-cancers-percent-all-ages-3-year-average-mfp",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/current/mortality-from-acute-myocardial-infarction-or-ischeamic-heart-disease-other-than-acute-myocardial-infarction/mortality-from-acute-myocardial-infarction-directly-standardised-rate-all-ages-3-year-average-mfp/mortality-from-acute-myocardial-infarction-directly-standardised-rate-all-ages-3-year-average-mfp",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/current/years-of-life-lost/years-of-life-lost-due-to-mortality-from-malignant-melanoma-crude-rate-1-74-years-3-year-average-mfp/years-of-life-lost-due-to-mortality-from-malignant-melanoma-crude-rate-1-74-years-3-year-average-mfp",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/current/mortality-from-suicide-or-suicide-and-injury-undetermined/mortality-from-suicide-and-injury-undetermined-directly-standardised-rate-15-years-annual-trend-mfp/mortality-from-suicide-and-injury-undetermined-directly-standardised-rate-15-years-annual-trend-mfp",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/current/mortality-from-respiratory-diseases/mortality-from-pneumonia-indirectly-standardised-ratio-smr-75-years-3-year-average-mfp/mortality-from-pneumonia-indirectly-standardised-ratio-smr-75-years-3-year-average-mfp",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/current/years-of-life-lost/years-of-life-lost-due-to-mortality-from-stroke-crude-rate-1-74-years-3-year-average-mfp/years-of-life-lost-due-to-mortality-from-stroke-crude-rate-1-74-years-3-year-average-mfp",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/current/mortality-from-colorectal-cancer/mortality-from-colorectal-cancer-directly-standardised-rate-75-years-3-year-average-mfp/mortality-from-colorectal-cancer-directly-standardised-rate-75-years-3-year-average-mfp",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/current/mortality-from-all-cancers/mortality-from-all-cancers-directly-standardised-rate-65-74-years-annual-trend-mfp/mortality-from-all-cancers-directly-standardised-rate-65-74-years-annual-trend-mfp",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-local-basket-of-inequality-indicators-lboi/current/local-basket-of-inequality-indicators-lboi/lboi-indicator-3-1-percentage-of-11-year-olds-achieving-the-expected-level-4-or-above-in-maths-and-english-key-stage-2/lboi-indicator-3-1-percentage-of-11-year-olds-achieving-the-expected-level-4-or-above-in-maths-and-english-key-stage-2",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/current/mortality-from-prostate-cancer/mortality-from-prostate-cancer-directly-standardised-rate-75-years-3-year-average-m/mortality-from-prostate-cancer-directly-standardised-rate-75-years-3-year-average-m",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/current/mortality-from-leukaemia/mortality-from-leukaemia-directly-standardised-rate-all-ages-annual-trend-mfp/mortality-from-leukaemia-directly-standardised-rate-all-ages-annual-trend-mfp",
		"/publications/clinical-indicators/adult-social-care-outcomes-framework-ascof/current/safeguarding-people-whose-circumstances-make-them-vulnerable-and-protecting-from-avoidable-harm/4a-proportion-of-people-who-use-services-who-feel-safe/4a-proportion-of-people-who-use-services-who-feel-safe",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/current/mortality-from-stomach-cancer/mortality-from-stomach-cancer-indirectly-standardised-ratio-smr-all-ages-annual-trend-mfp/mortality-from-stomach-cancer-indirectly-standardised-ratio-smr-all-ages-annual-trend-mfp",
		"/publications/clinical-indicators/ccg-outcomes-indicator-set/current/domain-1-preventing-people-from-dying-prematurely-ccg/1-13-antenatal-assessments-within-13-weeks/1-13-antenatal-assessments-within-13-weeks",
		"/publications/clinical-indicators/nhs-outcomes-framework/current/domain-4-ensuring-that-people-have-a-positive-experience-of-care-nof/4-3-patient-experience-of-a-e-services/4-3-patient-experience-of-a-e-services",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-public-health/current/alcohol-consumption/alcohol-consumption-proportion-who-drank-more-than-4-units-and-8-units-males-and-3-units-and-6-units-females-on-any-one-day-last-week-percent-16-years-annual-trend-mf/alcohol-consumption-proportion-who-drank-more-than-4-units-and-8-units-males-and-3-units-and-6-units-females-on-any-one-day-last-week-percent-16-years-annual-trend-mf",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/current/years-of-life-lost/years-of-life-lost-due-to-mortality-from-bronchitis-and-emphysema-crude-rate-1-74-years-3-year-average-mfp/years-of-life-lost-due-to-mortality-from-bronchitis-and-emphysema-crude-rate-1-74-years-3-year-average-mfp",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/current/mortality-from-hodgkin-s-disease/mortality-from-hodgkin-s-disease-directly-standardised-rate-all-ages-annual-trend-mfp/mortality-from-hodgkin-s-disease-directly-standardised-rate-all-ages-annual-trend-mfp",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-public-health/current/circulatory-diseases/diastolic-blood-pressure-standardised-mean-16-years-annual-trend-mfp/diastolic-blood-pressure-standardised-mean-16-years-annual-trend-mfp",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/current/mortality-from-bladder-cancer/mortality-from-bladder-cancer-indirectly-standardised-ratio-smr-75-years-3-year-average-mfp/mortality-from-bladder-cancer-indirectly-standardised-ratio-smr-75-years-3-year-average-mfp",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/current/mortality-from-all-causes/mortality-from-all-causes-directly-standardised-rate-65-74-years-annual-trend-mfp/mortality-from-all-causes-directly-standardised-rate-65-74-years-annual-trend-mfp",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-public-health/current/general-health/limiting-longstanding-illness-standardised-percent-16-years-annual-trend-mfp/limiting-longstanding-illness-standardised-percent-16-years-annual-trend-mfp",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/current/years-of-life-lost/years-of-life-lost-due-to-mortality-from-leukaemia-directly-standardised-rate-1-74-years-3-year-average-mfp/years-of-life-lost-due-to-mortality-from-leukaemia-directly-standardised-rate-1-74-years-3-year-average-mfp",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/current/mortality-from-chronic-rheumatic-heart-disease/mortality-from-chronic-rheumatic-heart-disease-indirectly-standardised-ratio-smr-5-44-years-3-year-average-mfp/mortality-from-chronic-rheumatic-heart-disease-indirectly-standardised-ratio-smr-5-44-years-3-year-average-mfp",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/current/mortality-from-accidents/mortality-from-accidents-indirectly-standardised-ratio-smr-85-years-3-year-average-mfp/mortality-from-accidents-indirectly-standardised-ratio-smr-85-years-3-year-average-mfp",
		"/publications/clinical-indicators/adult-social-care-outcomes-framework-ascof/current/enhancing-quality-of-life-for-people-with-care-and-support-needs/1b-proportion-of-people-who-use-services-who-have-control-over-their-daily-life/1b-proportion-of-people-who-use-services-who-have-control-over-their-daily-life",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-local-basket-of-inequality-indicators-lboi/current/local-basket-of-inequality-indicators-lboi/lboi-indicator-8-7-emergency-admissions-rate-for-asthma-and-diabetes-per-100-000-population-age-and-sex-standardised/lboi-indicator-8-7-emergency-admissions-rate-for-asthma-and-diabetes-per-100-000-population-age-and-sex-standardised",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/current/mortality-from-gastric-duodenal-and-peptic-ulcers/mortality-from-gastric-duodenal-and-peptic-ulcers-indirectly-standardised-ratio-smr-75-years-3-year-average-mfp/mortality-from-gastric-duodenal-and-peptic-ulcers-indirectly-standardised-ratio-smr-75-years-3-year-average-mfp",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-hospital-care/current/emergency-readmissions-to-hospital-within-28-days-of-discharge/emergency-readmissions-to-hospital-within-28-days-of-discharge-indirectly-standardised-percent-16-74-years-annual-trend-f/emergency-readmissions-to-hospital-within-28-days-of-discharge-indirectly-standardised-percent-16-74-years-annual-trend-f",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/current/mortality-from-prostate-cancer/mortality-from-prostate-cancer-directly-standardised-rate-all-ages-3-year-average-m/mortality-from-prostate-cancer-directly-standardised-rate-all-ages-3-year-average-m",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-mortality/current/years-of-life-lost/years-of-life-lost-due-to-mortality-from-suicide-directly-standardised-rate-15-74-years-3-year-average-mfp/years-of-life-lost-due-to-mortality-from-suicide-directly-standardised-rate-15-74-years-3-year-average-mfp",
		"/publications/clinical-indicators/compendium-of-population-health-indicators/compendium-local-basket-of-inequality-indicators-lboi/current/local-basket-of-inequality-indicators-lboi/lboi-indicator-12-4-acceptable-waiting-times-for-assessment-for-older-people/lboi-indicator-12-4-acceptable-waiting-times-for-assessment-for-older-people",
	)

	var searches = List(
		"/search?query=compendium",
		"/search/DOCUMENT+TYPE/publicationsystem_x003a_series?query=compendium"
	)

	var executions = Seq(
		exec(http("/publications").get("/publications")),
		exec(http("/").get("/")),
		exec(http("ci-hub ccg-outcomes").get("/publications/ci-hub/ccg-outcomes")),
		exec(http("ci-hub compendium-indicators").get("/publications/ci-hub/compendium-indicators")),
		exec(http("ci-hub social-care").get("/publications/ci-hub/social-care"))
		exec(http("ci-hub nhs-outcomes-framework").get("/publications/ci-hub/nhs-outcomes-framework")),
		exec(http("ci-hub summary-hospital-level-mortality-indicator-shmi").get("/publications/ci-hub/summary-hospital-level-mortality-indicator-shmi")),
		exec(http("ci-hub patient-online-management-information-pomi").get("/publications/ci-hub/patient-online-management-information-pomi"))
	)

	series.zipWithIndex.foreach { case(el, i) => {
		executions = executions :+ exec( http(s"series document #$i").get(el) )
	} }

	publications.zipWithIndex.foreach { case(el, i) => {
		executions = executions :+ exec( http(s"publications document #$i").get(el) )
	} }

	datasets.zipWithIndex.foreach { case(el, i) => {
		executions = executions :+ exec( http(s"datasets document #$i").get(el) )
	} }

	searches.zipWithIndex.foreach { case(el, i) => {
		executions = executions :+ exec( http(s"search #$i").get(el) )
	} }

	val scn = scenario("PublicationSystemLoadTest").exec(Random.shuffle(executions))
}
