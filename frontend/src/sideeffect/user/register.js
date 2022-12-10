import {call, put, takeLatest} from "redux-saga/effects";
import {login, register} from "../../api/user";
import {user} from "../../data/user";
import {push} from "connected-react-router";

function* callRegister({payload}) {
  yield put({type: "spinner/showSpinner"});
  const {password} = payload;
  try {
    const {username} = yield call(register, payload);
    const response = yield call(login, {username, password});

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

export default function* watchRegister() {
  yield takeLatest(user.register.type, callRegister);
}
