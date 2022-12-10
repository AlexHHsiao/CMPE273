import {call, put, select, takeLatest} from "redux-saga/effects";
import {getAllHome} from "../../api/home";
import {home} from "../../data/home";
import {selectUserData} from "../../view/selector/user";

function* callGetAllHome({payload}) {
  yield put({type: "spinner/showSpinner"});
  const userData = yield select(selectUserData);
  const userOption = {userId: userData.id, negation: true};

  try {
    const response = yield call(getAllHome, payload, userOption);
    yield put({type: "home/getAllHomeSuccess", payload: response});
  } catch ({message, code = 500}) {
    yield put({type: "message/errorMessage", payload: `${code} : ${message}`});
  }

  yield put({type: "spinner/hideSpinner"});
}

export default function* watchGetAllHome() {
  yield takeLatest(home.getAllHome.type, callGetAllHome);
}
