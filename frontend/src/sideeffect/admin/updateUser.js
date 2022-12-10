import {call, put, takeLatest} from "redux-saga/effects";
import {update} from "../../api/user";
import {admin} from "../../data/admin";

function* callUpdateUser({payload}) {
  yield put({type: "spinner/showSpinner"});

  try {
    const response = yield call(update, payload, {isActive: true});
    yield put({type: "admin/updateUserSuccess", payload: {id: payload, data: response}});
    yield put({type: "message/successMessage", payload: "User has been approved"});
  } catch ({message, code = 500}) {
    yield put({type: "message/errorMessage", payload: `${code} : ${message}`});
  }

  yield put({type: "spinner/hideSpinner"});
}

export default function* watchUpdateUser() {
  yield takeLatest(admin.updateUser.type, callUpdateUser);
}
