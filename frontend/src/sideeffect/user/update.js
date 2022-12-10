import {call, put, takeLatest, select} from "redux-saga/effects";
import {update} from "../../api/user";
import {user} from "../../data/user";
import {selectUserData} from "../../view/selector/user";

function* callUpdate({payload}) {
  yield put({type: "spinner/showSpinner"});
  const userData = yield select(selectUserData);

  try {
    const response = yield call(update, userData.id, payload);
    yield put({type: "user/loginSuccess", payload: response});
    yield put({
      type: "message/successMessage",
      payload: "Your information has been updated!",
    });
  } catch ({message, code = 500}) {
    yield put({type: "message/errorMessage", payload: `${code} : ${message}`});
  }

  yield put({type: "spinner/hideSpinner"});
}

export default function* watchUpdate() {
  yield takeLatest(user.update.type, callUpdate);
}
