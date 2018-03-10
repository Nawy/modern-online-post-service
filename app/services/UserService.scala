package services

import javax.inject._

import model.UserInfo


@Singleton
class UserService {

  def insert(user: UserInfo): Long = {0}

  def update(user: UserInfo): Unit = {}

  def get(id: Long): UserInfo = UserInfo(Some(1L),"test","test",null,null)

}
