import {call, put, takeLatest} from "redux-saga/effects";
import {deleteUser} from "../../api/admin";
import {admin} from "../../data/admin";

function* callDeleteUser({payload}) {
  yield put({type: "spinner/showSpinner"});

  try {
    yield call(deleteUser, payload);
    yield put({type: "admin/deleteUserSuccess", payload});
    yield put({type: "home/logoutSuccess"});
    yield put({type: "message/successMessage", payload: "User has been removed "});
  } catch ({message, code = 500}) {
    yield put({type: "message/errorMessage", payload: `${code} : ${message}`});
  }

  yield put({type: "spinner/hideSpinner"});
}

export default function* watchDeleteUser() {
  yield takeLatest(admin.deleteUser.type, callDeleteUser);
}
