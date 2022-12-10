import {call, put, takeLatest} from "redux-saga/effects";
import {removeUserHome} from "../../api/home";
import {home} from "../../data/home";

function* callRemoveUserHome({payload}) {
  yield put({type: "spinner/showSpinner"});

  try {
    yield call(removeUserHome, payload);
    yield put({type: "home/removeUserHomeSuccess", payload});
    yield put({
      type: "message/successMessage",
      payload: "Home has been removed",
    });
  } catch ({message, code = 500}) {
    yield put({type: "message/errorMessage", payload: `${code} : ${message}`});
  }

  yield put({type: "spinner/hideSpinner"});
}

export default function* watchRemoveUserHome() {
  yield takeLatest(home.removeUserHome.type, callRemoveUserHome);
}
