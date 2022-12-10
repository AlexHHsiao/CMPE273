import React from "react";
import {Spin} from "antd";
import "./spinner.scss";

const Spinner = ({children, load}) => (
  <Spin spinning={load} tip="Loading...">
    {children}
  </Spin>
);

export default Spinner;
