import {call, put, takeLatest} from "redux-saga/effects";
import {getOffer} from "../../api/home";
import {home} from "../../data/home";

function* callGetOffer({payload}) {
  yield put({type: "spinner/showSpinner"});

  try {
    const response = yield call(getOffer, payload);
    yield put({type: "home/getOfferSuccess", payload: response});
  } catch ({message, code = 500}) {
    yield put({type: "message/errorMessage", payload: `${code} : ${message}`});
  }

  yield put({type: "spinner/hideSpinner"});
}

export default function* watchGetOffer() {
  yield takeLatest(home.getOffer.type, callGetOffer);
}
