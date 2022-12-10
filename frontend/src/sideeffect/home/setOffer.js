import {call, put, takeLatest} from "redux-saga/effects";
import {setOffer} from "../../api/home";
import {home} from "../../data/home";

function* callSetOffer({payload}) {
  yield put({type: "spinner/showSpinner"});

  try {
    yield call(setOffer, payload);

    yield put({type: "home/setOfferSuccess", payload});
    yield put({
      type: "message/successMessage",
      payload: "Request has been sent. Please wait for owner approval",
    });
  } catch ({message, code = 500}) {
    yield put({type: "message/errorMessage", payload: `${code} : ${message}`});
  }

  yield put({type: "spinner/hideSpinner"});
}

export default function* watchSetOffer() {
  yield takeLatest(home.setOffer.type, callSetOffer);
}
