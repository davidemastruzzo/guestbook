package ch.bbw.guestbook.rest;

public interface WebServiceUrl {


  String Prefix = "/rest";

  String User = Prefix + "/user";
  String UserAccount = User + "/account";

  String Guestbook = Prefix + "/guestbook";

}
