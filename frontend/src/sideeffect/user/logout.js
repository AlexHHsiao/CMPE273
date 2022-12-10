import {call, put, takeLatest} from "redux-saga/effects";
import {logout} from "../../api/user";
import {user} from "../../data/user";

function* callLogout() {
  yield put({type: "spinner/showSpinner"});

  try {
    yield call(logout);
    yield put({type: "user/logoutSuccess"});
    yield put({type: "home/logoutSuccess"});
    yield put({type: "admin/logoutSuccess"});
  } catch ({message, code = 500}) {
    yield put({type: "message/errorMessage", payload: `${code} : ${message}`});
  }

  yield put({type: "spinner/hideSpinner"});
}

export default function* watchLogout() {
  yield takeLatest(user.logout.type, callLogout);
}
