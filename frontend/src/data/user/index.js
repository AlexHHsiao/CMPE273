import {createSlice, createAction} from "@reduxjs/toolkit";

const login = createAction("user/login");
const register = createAction("user/register");
const logout = createAction("user/logout");
const update = createAction("user/update");

export const user = {
  login,
  register,
  logout,
  update,
};

const userSlice = createSlice({
  name: "user",
  initialState: {
    data: null,
  },
  reducers: {
    loginSuccess: (state, {payload}) => {
      state.data = payload;
    },
    logoutSuccess: (state) => {
      state.data = null;
    },
  },
});

export default userSlice.reducer;
