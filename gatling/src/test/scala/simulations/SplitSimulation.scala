package scala.simulations

import java.util.concurrent.TimeUnit

import java.util.concurrent.atomic.AtomicInteger
import scala.concurrent.duration._
import io.gatling.core.Predef._
import io.gatling.http.Predef._

class SplitSimulation  extends Simulation {

  val id = new AtomicInteger(0)
  val numberfeed: Iterator[Map[String, Int]] = Iterator.continually(Map("number" -> id.getAndIncrement()))

  val isDocker = true

  val urlBase = if (isDocker) "http://webservice.docker.localhost" else "http://localhost:8081"

  val espera = "1"
  //print(urlBase)
  val httpProtocol = http.baseUrl(urlBase).acceptHeader("application/json, text/plain, */*")

  val scn = scenario("SplitSimulation")
    .feed(numberfeed)
    .exec(
      http("Request")
        //.post("/resquest")
        .post("/resquest/${number}")
        .check(bodyString.saveAs("Request_Response"))
        .check(status.in(200 to 210))
    )
    //.pause(espera, TimeUnit.SECONDS)

    .doWhile(session => !session("result").as[String].contains("true")){
      exec(
        http("Result")
          .post("/result")
          .header("Content-Type", "application/json")
          .header("Accept", "application/json")
          .body(StringBody("""{ "value": "${Request_Response}" }"""))
          .check(status.in(200 to 210))
          .check(bodyString.saveAs("result"))
      )

    }
  .exec { session =>
    // displays the content of the session in the console (debugging only)
    println(session("number"))

    // return the original session
    session
  }
    .exitHereIfFailed

  setUp(
    //scn.inject(atOnceUsers(100))
    scn.inject(rampUsers(1000).during(60.seconds))
  ).protocols(httpProtocol)

}
