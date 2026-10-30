import com.twitter.finagle.{Http, Service}
import com.twitter.finagle.http
import com.twitter.util.{Await, Future}

object Client extends App {
  val client: Service[http.Request, http.Response] = Http.client
    .withTls("reqbin.com")
    .newService("reqbin.com:443")

  val request = http.Request(http.Method.Get, "/echo/get/json")
  request.headerMap.add("Host", "reqbin.com")
  request.headerMap.add("Accept", "application/json")
  
  val response: Future[http.Response] = client(request)
  Await.result { 
    response.onSuccess { (rep: http.Response) =>
      // Always return HTTP 403 due to Cloudflare
      println(s"GET success: $rep") 
    }
  }
}
