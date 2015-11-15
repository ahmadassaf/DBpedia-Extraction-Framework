package org.dbpedia.extraction.destinations

/**
 * Defines the datasets which are extracted by DBpedia.
 * TODO: add references to the extractor classes.
 */
object DBpediaDatasets
{
    /**
     * General
     */
    val Labels = new Dataset("labels")
    val CategoryLabels = new Dataset("category_labels")
    val Images = new Dataset("images")
    val GeoCoordinates = new Dataset("geo_coordinates")
    val Persondata = new Dataset("persondata_unredirected")
    val Pnd = new Dataset("pnd")
    val Redirects = new Dataset("redirects")
    val ArticleCategories = new Dataset("article_categories")
    val ArticleTemplates = new Dataset("article_templates")
    val SkosCategories = new Dataset("skos_categories")
    val RevisionUris = new Dataset("revision_uris")
    val RevisionIds = new Dataset("revision_ids")
    val RevisionMeta = new Dataset("revision_meta")
    val PageIds = new Dataset("page_ids")
    val InterLanguageLinks = new Dataset("interlanguage_links")
    val Genders = new Dataset("genders")
    val TopicalConcepts = new Dataset("topical_concepts_unredirected")
    val IriSameAsUri = new Dataset("iri_same_as_uri")
    val FlickrWrapprLinks = new Dataset("flickr_wrappr_links")
    val PageLength = new Dataset("page_length")

    /**
     * Mapping based
     */
    val OntologyTypes = new Dataset("instance_types")
<<<<<<< HEAD
    val OntologyProperties = new Dataset("mappingbased_properties")
=======
    val OntologyTypesTransitive = new Dataset("instance_types_transitive")
    val OntologyPropertiesObjects = new Dataset("mappingbased_objects_uncleaned_unredirected")   //TODO changes here should be reflected to the related wikidata dataset
    val OntologyPropertiesLiterals = new Dataset("mappingbased_literals")
    val OntologyPropertiesGeo = new Dataset("geo_coordinates_mappingbased")
>>>>>>> 2dfd3a4888d6f710311813ddd6f9ddffeea46195
    val SpecificProperties = new Dataset("specific_mappingbased_properties")
    
    /**
     * French population template
     */
     val FrenchPopulation = new Dataset("french_population")

    /**
     *  Infobox
     */
    val InfoboxProperties = new Dataset("infobox_properties_unredirected")
    val InfoboxPropertyDefinitions = new Dataset("infobox_property_definitions")
    val TemplateParameters = new Dataset("template_parameters")
    val InfoboxTest = new Dataset("infobox_test")

    /**
     * Abstracts
     */
    val ShortAbstracts = new Dataset("short_abstracts")
    val LongAbstracts = new Dataset("long_abstracts")

    /**
     * Links
     */
    val LinksToWikipediaArticle = new Dataset("wikipedia_links")
    val ExternalLinks = new Dataset("external_links")
    val PageLinks = new Dataset("page_links_unredirected")
    val DisambiguationLinks  = new Dataset("disambiguations_unredirected")
    val Homepages = new Dataset("homepages")
    val OutDegree = new Dataset("out_degree")
    

    /**
     * Files
     */
    val FileInformation = new Dataset("file_information")
    

    /**
     * Wikidata outputDatasets
     */
<<<<<<< HEAD
     
    //for the dummy wikidata Extractor skeleton file
    val Wikidata = new Dataset("wikidata")
    
    //language links dump in the form of
    //<http://L1.dbpedia.org/resource/X2> <http://www.w3.org/2002/07/owl#sameAs> <http://L2.dbpedia.org/resource/X2> .
    val WikidataLL = new Dataset("wikidata-ll")
    
    //Multi lingual labels triples 
    //<http://wikidata.dbpedia.org/resource/Q549> <http://www.w3.org/2000/01/rdf-schema#label> "Bojovnica pestrá"@sk .
    val WikidataLabels = new Dataset("wikidata-labels")
    
    //mappings between Wikidata entities inside DBpedia and DBpedia entities using the owl:sameas property
    //<http://wikidata.dbpedia.org/resource/Q1934> <http://www.w3.org/2002/07/owl#sameAs> <http://ar.dbpedia.org/resource/سيدني_غوفو> .
    val WikidataSameAs = new Dataset("wikidata-sameas")
    
    //Mapping between Wikidata Entities URIs and Their Equivalent URIs used in DBpedia 
    //<http://wikidata.dbpedia.org/resource/Q18> <http://www.w3.org/2002/07/owl#sameAs> <http://wikidata.org/entity/Q18> .
    val WikidataNameSpaceSameAs = new Dataset("wikidata-namespace-sameas")
    
    // wikidata facts triples 
    val WikidataFacts = new Dataset("wikidata-facts")
    //wikidata facts triples with mapped properties to DBpedia ones 
    val WikidataMappedFacts = new Dataset("wikidata-mapped")

    //wikidata alias output
    //<http://wikidata.dbpedia.org/resource/Q446> <http://dbpedia.org/ontology/alias> "alias"@lang .
    val WikidataAlias = new Dataset("wikidata-alias")

    //wikidata description output
    //wikidata.dbpedia.org/resource/Q139> <http://dbpedia.org/ontology/description> "description"@lang.
    val WikidataDescription = new Dataset("wikidata-description")
=======
    val WikidataLabelsMappingsWiki = new Dataset("labels")
    val WikidataLabelsRest = new Dataset("labels-nmw")
    val WikidataSameAs = new Dataset("sameas-all-wikis")
    val WikidataNameSpaceSameAs = new Dataset("sameas-wikidata")
    val WikidataSameAsExternal = new Dataset("sameas-external")
    val WikidataAliasMappingsWiki = new Dataset("alias")
    val WikidataAliasRest = new Dataset("alias-nmw")
    val WikidataDescriptionMappingsWiki = new Dataset("description")
    val WikidataDescriptionRest = new Dataset("description-nmw")
    val WikidataProperty= new Dataset("properties")
    val WikidataR2R_literals = OntologyPropertiesLiterals
    val WikidataR2R_objects = OntologyPropertiesObjects
    val WikidataR2R_ontology = new Dataset("ontology-subclassof")
    val WikidataReifiedR2R = new Dataset("mappingbased_properties-reified") // keep same name with other languages
    val WikidataReifiedR2RQualifier= new Dataset("mappingbased_properties-reified-qualifiers") // keep same name with other languages
    val WikidataRaw = new Dataset("raw_unredirected")
    val WikidataRawReified = new Dataset("raw-reified")
    val WikidataRawReifiedQualifiers = new Dataset("raw-reified-qualifiers")
    val WikidataReference = new Dataset("references")

    /**
     * Citations
     */
    val CitationLinks = new Dataset("citation_links")
    val CitationData = new Dataset("citation_data")
    val CitationTypes = new Dataset("citation_types")
>>>>>>> 2dfd3a4888d6f710311813ddd6f9ddffeea46195
}
