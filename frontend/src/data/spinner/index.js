import {createSlice} from "@reduxjs/toolkit";

const spinnerSlice = createSlice({
  name: "spinner",
  initialState: {
    data: false,
  },
  reducers: {
    showSpinner: (state, {payload}) => {
      state.data = true;
    },
    hideSpinner: (state, {payload}) => {
      state.data = false;
    },
  },
});

export default spinnerSlice.reducer;
