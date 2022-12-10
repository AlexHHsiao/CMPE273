import {call, put, takeLatest} from "redux-saga/effects";
import {setUserHome} from "../../api/home";
import {home} from "../../data/home";

function* callSetUserHom({payload}) {
  yield put({type: "spinner/showSpinner"});

  try {
    const response = yield call(setUserHome, payload);
    yield put({type: "home/setUserHomeSuccess", payload: response});
  } catch ({message, code = 500}) {
    yield put({type: "message/errorMessage", payload: `${code} : ${message}`});
  }

  yield put({type: "spinner/hideSpinner"});
}

export default function* watchSetUserHome() {
  yield takeLatest(home.setUserHome.type, callSetUserHom);
}
