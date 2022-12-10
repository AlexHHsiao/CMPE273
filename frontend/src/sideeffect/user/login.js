import {call, put, takeLatest} from "redux-saga/effects";
import {login} from "../../api/user";
import {user} from "../../data/user";
import {push} from "connected-react-router";

function* callLogin({payload}) {
  yield put({type: "spinner/showSpinner"});

  try {
    const response = yield call(login, payload);

    if (response.isActive) {
      yield put({type: "user/loginSuccess", payload: response});
      yield put(push("/"));
    } else {
      yield put(push("/pending"));
    }
  } catch ({message, code = 500}) {
    yield put({type: "message/errorMessage", payload: `${code} : ${message}`});
  }

  yield put({type: "spinner/hideSpinner"});
}

export default function* watchLogin() {
  yield takeLatest(user.login.type, callLogin);
}
