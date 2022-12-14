import React from "react";
import ReactDOM from "react-dom";
import AppRoutes from "./route";
import * as serviceWorker from "./serviceWorker";
import "antd/dist/antd.css";
import {Provider} from "react-redux";
import {enableMapSet} from "immer";
import store, {history} from "./store";
import {ConnectedRouter} from "connected-react-router";

enableMapSet();

ReactDOM.render(
  <Provider store={store}>
    <ConnectedRouter history={history}>
      <AppRoutes />
    </ConnectedRouter>
  </Provider>,
  document.getElementById("root")
);

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();
