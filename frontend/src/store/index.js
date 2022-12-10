import {createStore, compose, applyMiddleware} from "redux";
import createSagaMiddleware from "redux-saga";
import {combineReducers} from "redux";
import userReducer from "../data/user";
import appSagas from "../sideeffect";
import spinnerReducer from "../data/spinner";
import messageReducer from "../data/message";
import adminReducer from "../data/admin";
import homeReducer from "../data/home";
import {connectRouter} from "connected-react-router";
import {createBrowserHistory} from "history";
import {routerMiddleware} from "connected-react-router";

export const history = createBrowserHistory();

const rootReducer = (history) =>
  combineReducers({
    user: userReducer,
    spinner: spinnerReducer,
    message: messageReducer,
    admin: adminReducer,
    home: homeReducer,
    router: connectRouter(history),
  });

const sagaMiddleware = createSagaMiddleware();
const store = compose(
  applyMiddleware(routerMiddleware(history), sagaMiddleware),
  window.devToolsExtension ? window.devToolsExtension() : (f) => f
)(createStore)(rootReducer(history));

//   window.devToolsExtension && window.devToolsExtension()

sagaMiddleware.run(appSagas);

export default store;
