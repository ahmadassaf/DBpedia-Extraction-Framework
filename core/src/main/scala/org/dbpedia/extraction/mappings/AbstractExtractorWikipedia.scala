package org.dbpedia.extraction.mappings

import java.net.URL

import org.dbpedia.extraction.ontology.Ontology
import org.dbpedia.extraction.util.Language

import scala.language.reflectiveCalls

/**
 * User: Dimitris Kontokostas
 * Description
 * Created: 5/19/14 9:21 AM
 */
class AbstractExtractorWikipedia(
  context : {
    def ontology : Ontology
    def language : Language
  })
  extends AbstractExtractor (context)
{

<<<<<<< HEAD
  override def apiUrl: String = "http://" + context.language.wikiCode + ".wikipedia.org/w/api.php"
=======
  override def apiUrl = new URL("https://" + context.language.wikiCode + ".wikipedia.org/w/api.php")
>>>>>>> 594261ac5fae789587c40ae2bfe473b8ae003aa5
}
