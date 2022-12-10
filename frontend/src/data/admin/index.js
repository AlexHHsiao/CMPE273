import {createSlice, createAction} from "@reduxjs/toolkit";
import {adminRole} from "../../utils/constants/API";

const getAllUser = createAction("admin/getAllUser");
const deleteUser = createAction("admin/deleteUser");
const updateUser = createAction("admin/updateUser");

export const admin = {
  getAllUser,
  deleteUser,
  updateUser,
};

const adminSlice = createSlice({
  name: "admin",
  initialState: {
    pending: null,
    current: null,
  },
  reducers: {
    getAllUserSuccess: (state, {payload}) => {
      const pendingUserData = payload.filter(
        ({isActive, role}) => !isActive && role !== adminRole
      );

      const currentUserData = payload.filter(
        ({isActive, role}) => isActive && role !== adminRole
      );
      state.pending = pendingUserData;
      state.current = currentUserData;
    },
    updateUserSuccess: (state, {payload: {id, data}}) => {
      state.current.push(data);
      state.pending = state.pending.filter(({id: userId}) => userId !== id);
    },
    deleteUserSuccess: (state, {payload}) => {
      state.pending = state.pending.filter(({id}) => id !== payload);
      state.current = state.current.filter(({id}) => id !== payload);
    },
    logoutSuccess: (state) => {
      state.pending = null;
      state.current = null;
    },
  },
});

export default adminSlice.reducer;
