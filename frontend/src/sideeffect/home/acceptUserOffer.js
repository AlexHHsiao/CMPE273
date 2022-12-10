import {call, put, takeLatest} from "redux-saga/effects";
import {acceptUserOffer} from "../../api/home";
import {home} from "../../data/home";

function* callAcceptUserOffer({payload: {userId, homeId}}) {
  yield put({type: "spinner/showSpinner"});

  try {
    yield call(acceptUserOffer, homeId, userId);
    yield put({type: "home/acceptUserOfferSuccess", payload: homeId});
    yield put({type: "message/successMessage", payload: "Offer has been accepted"});
  } catch ({message, code = 500}) {
    yield put({type: "message/errorMessage", payload: `${code} : ${message}`});
  }

  yield put({type: "spinner/hideSpinner"});
}

export default function* watchAcceptUserOffer() {
  yield takeLatest(home.acceptUserOffer.type, callAcceptUserOffer);
}
