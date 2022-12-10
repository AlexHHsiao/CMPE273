import {all, fork} from "redux-saga/effects";
import watchLogin from "./user/login";
import watchRegister from "./user/register";
import watchGetAllUser from "./admin/getAllUser";
import watchLogout from "./user/logout";
import watchUpdate from "./user/update";
import watchDeleteUser from "./admin/deleteUser";
import watchUpdateUser from "./admin/updateUser";
import watchGetAllHome from "./home/getAllHome";
import watchGetAllFav from "./home/getAllFav";
import watchSetFavHome from "./home/setFavHome";
import watchRemoveFavHome from "./home/removeFavHome";
import watchGetUserHome from "./home/getUserHome";
import watchSetUserHome from "./home/setUserHome";
import watchGetOffer from "./home/getOffer";
import watchRemoveUserHome from "./home/removeUserHome";
import watchSetOffer from "./home/setOffer";
import watchAcceptUserOffer from "./home/acceptUserOffer";
import watchUpdateUserHome from "./home/updateUserHome";
import watchGetFavSearch from "./home/getFavSearch";
import watchSetFavSearch from "./home/setFavSearch";

function* appSagas() {
  yield all([fork(watchLogin)]);
  yield all([fork(watchRegister)]);
  yield all([fork(watchGetAllUser)]);
  yield all([fork(watchLogout)]);
  yield all([fork(watchUpdate)]);
  yield all([fork(watchDeleteUser)]);
  yield all([fork(watchUpdateUser)]);
  yield all([fork(watchGetAllHome)]);
  yield all([fork(watchGetAllFav)]);
  yield all([fork(watchSetFavHome)]);
  yield all([fork(watchRemoveFavHome)]);
  yield all([fork(watchGetUserHome)]);
  yield all([fork(watchSetUserHome)]);
  yield all([fork(watchGetOffer)]);
  yield all([fork(watchSetOffer)]);
  yield all([fork(watchRemoveUserHome)]);
  yield all([fork(watchAcceptUserOffer)]);
  yield all([fork(watchUpdateUserHome)]);
  yield all([fork(watchGetFavSearch)]);
  yield all([fork(watchSetFavSearch)]);
}

export default appSagas;
