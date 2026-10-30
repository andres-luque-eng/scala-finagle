
import com.twitter.finagle.{Http, Service}
import com.twitter.finagle.http.{Request, Response}
import com.twitter.util.Await

object Proxy extends App {
  val client: Service[Request, Response] =
    Http.newService("twitter.com:80")

  val server = Http.serve(":7070", client)
  Await.ready(server)
}