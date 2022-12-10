import {call, put, takeLatest} from "redux-saga/effects";
import {removeFavHome} from "../../api/home";
import {home} from "../../data/home";

function* callRemoveFavHome({payload}) {
  yield put({type: "spinner/showSpinner"});

  try {
    yield call(removeFavHome, payload);
    yield put({type: "home/removeFavHomeSuccess", payload});
    yield put({
      type: "message/successMessage",
      payload: "Home has been removed from favorite list",
    });
  } catch ({message, code = 500}) {
    yield put({type: "message/errorMessage", payload: `${code} : ${message}`});
  }

  yield put({type: "spinner/hideSpinner"});
}

export default function* watchRemoveFavHome() {
  yield takeLatest(home.removeFavHome().type, callRemoveFavHome);
}
