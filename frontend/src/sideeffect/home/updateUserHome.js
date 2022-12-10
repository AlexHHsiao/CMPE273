import {call, put, takeLatest} from "redux-saga/effects";
import {updateUserHome} from "../../api/home";
import {home} from "../../data/home";

function* callUpdateUserHom({payload: {id, body}}) {
  yield put({type: "spinner/showSpinner"});

  try {
    const response = yield call(updateUserHome, id, body);
    yield put({type: "home/updateUserHomeSuccess", payload: response});
    yield put({type: "message/successMessage", payload: "Home has been updated"});
  } catch ({message, code = 500}) {
    yield put({type: "message/errorMessage", payload: `${code} : ${message}`});
  }

  yield put({type: "spinner/hideSpinner"});
}

export default function* watchUpdateUserHome() {
  yield takeLatest(home.updateUserHome.type, callUpdateUserHom);
}
