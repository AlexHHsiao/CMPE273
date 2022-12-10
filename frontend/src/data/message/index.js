import {createSlice} from "@reduxjs/toolkit";

const messageSlice = createSlice({
  name: "message",
  initialState: {
    data: null,
  },
  reducers: {
    errorMessage: (state, {payload}) => {
      state.data = {detail: payload, type: "error"};
    },
    warningMessage: (state, {payload}) => {
      state.data = {detail: payload, type: "warning"};
    },
    infoMessage: (state, {payload}) => {
      state.data = {detail: payload, type: "info"};
    },
    successMessage: (state, {payload}) => {
      state.data = {detail: payload, type: "success"};
    },
  },
});

export default messageSlice.reducer;
