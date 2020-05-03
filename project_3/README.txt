Check if it actualy runs for anybody other than me.
I spent what feels like half of my existance on this earth before realizing jena requires Java8.

compiling/executing should just involv running the do.bat script

form of queries:

SELECT ?x
WHERE { ?x <http://www.semanticweb.org/user/ontologies/2020/CovidOntology/1.0.0#hasCountry> <http://www.semanticweb.org/user/ontologies/2020/CovidOntology/1.0.0#Country_Canada> }

---------------------------------------

PREFIX j.0: <https://data.cdc.gov/resource/rpjd-ejph#>
PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>

SELECT ?deaths
WHERE
{
	?x j.0:all_causes_by_age_years_1_24 ?deaths .
	FILTER ( ?deaths < "4" )
}
	ORDER BY ?deaths