import {call, put, takeLatest} from "redux-saga/effects";
import {getFavSearch} from "../../api/home";
import {home} from "../../data/home";

function* callGetFavSearch({payload}) {
  yield put({type: "spinner/showSpinner"});

  try {
    const response = yield call(getFavSearch);
    yield put({type: "home/getFavSearchSuccess", payload: response});
  } catch ({message, code = 500}) {
    yield put({type: "message/errorMessage", payload: `${code} : ${message}`});
  }

  yield put({type: "spinner/hideSpinner"});
}

export default function* watchGetFavSearch() {
  yield takeLatest(home.getFavSearch.type, callGetFavSearch);
}
