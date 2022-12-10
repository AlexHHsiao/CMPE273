import {call, put, takeLatest} from "redux-saga/effects";
import {setFavHome} from "../../api/home";
import {home} from "../../data/home";

function* callSetFavHome({payload}) {
  yield put({type: "spinner/showSpinner"});

  try {
    yield call(setFavHome, payload.homeId);
    yield put({type: "home/setFavHomeSuccess", payload});
    yield put({
      type: "message/successMessage",
      payload: "Home has been added to favorite list",
    });
  } catch ({message, code = 500}) {
    yield put({type: "message/errorMessage", payload: `${code} : ${message}`});
  }

  yield put({type: "spinner/hideSpinner"});
}

export default function* watchSetFavHome() {
  yield takeLatest(home.setFavHome().type, callSetFavHome);
}
