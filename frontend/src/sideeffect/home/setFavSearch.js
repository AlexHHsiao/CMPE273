import {call, put, takeLatest} from "redux-saga/effects";
import {setFavSearch} from "../../api/home";
import {home} from "../../data/home";

function* callSetFavSearch({payload}) {
  yield put({type: "spinner/showSpinner"});

  try {
    yield call(setFavSearch, payload);
    yield put({type: "home/setFavSearchSuccess", payload});
    yield put({type: "message/successMessage", payload: "Search has been saved"});
  } catch ({message, code = 500}) {
    yield put({type: "message/errorMessage", payload: `${code} : ${message}`});
  }

  yield put({type: "spinner/hideSpinner"});
}

export default function* watchSetFavSearch() {
  yield takeLatest(home.setFavSearch.type, callSetFavSearch);
}
