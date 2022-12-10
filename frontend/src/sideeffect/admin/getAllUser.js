import {call, put, takeLatest} from "redux-saga/effects";
import {getAllUser} from "../../api/admin";
import {admin} from "../../data/admin";

function* callGetAllUser() {
  yield put({type: "spinner/showSpinner"});

  try {
    const response = yield call(getAllUser);
    yield put({type: "admin/getAllUserSuccess", payload: response});
  } catch ({message, code = 500}) {
    yield put({type: "message/errorMessage", payload: `${code} : ${message}`});
  }

  yield put({type: "spinner/hideSpinner"});
}

export default function* watchGetAllUser() {
  yield takeLatest(admin.getAllUser.type, callGetAllUser);
}
