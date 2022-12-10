import {call, put, takeLatest} from "redux-saga/effects";
import {getAllFav} from "../../api/home";
import {home} from "../../data/home";

function* callGetAllFav() {
  yield put({type: "spinner/showSpinner"});

  try {
    const response = yield call(getAllFav);
    yield put({type: "home/getAllFavSuccess", payload: response});
  } catch ({message, code = 500}) {
    yield put({type: "message/errorMessage", payload: `${code} : ${message}`});
  }

  yield put({type: "spinner/hideSpinner"});
}

export default function* watchGetAllFav() {
  yield takeLatest(home.getAllFav().type, callGetAllFav);
}
