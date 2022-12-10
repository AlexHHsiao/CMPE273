import {createSlice, createAction} from "@reduxjs/toolkit";

const getAllHome = createAction("home/getAllHome");
const getAllFav = createAction("home/getAllFav");
const setFavHome = createAction("home/setFavHome");
const removeFavHome = createAction("home/removeFavHome");
const getUserHome = createAction("home/getUserHome");
const getOffer = createAction("home/getOffer");
const setOffer = createAction("home/setOffer");
const setUserHome = createAction("home/setUserHome");
const updateUserHome = createAction("home/updateUserHome");
const removeUserHome = createAction("home/removeUserHome");
const acceptUserOffer = createAction("home/acceptUserOffer");
const getFavSearch = createAction("home/getFavSearch");
const setFavSearch = createAction("home/setFavSearch");

export const home = {
  getAllHome,
  getAllFav,
  setFavHome,
  removeFavHome,
  getUserHome,
  updateUserHome,
  getOffer,
  setOffer,
  setUserHome,
  removeUserHome,
  acceptUserOffer,
  getFavSearch,
  setFavSearch,
};

const homeSlice = createSlice({
  name: "home",
  initialState: {
    data: null,
    current: null,
    favHome: null,
    userHome: null,
    offer: null,
    favSearch: null,
  },
  reducers: {
    getAllHomeSuccess: (state, {payload}) => {
      state.data = payload;
    },
    getUserHomeSuccess: (state, {payload}) => {
      state.userHome = payload;
    },
    removeUserHomeSuccess: (state, {payload}) => {
      state.userHome = state.userHome.filter(({homeId}) => homeId !== payload);
    },
    setUserHomeSuccess: (state, {payload}) => {
      state.userHome.push(payload);
    },
    updateUserHomeSuccess: (state, {payload}) => {
      const index = state.userHome.findIndex(({homeId}) => homeId === payload.homeId);
      state.userHome[index] = payload;
    },
    acceptUserOfferSuccess: (state, {payload}) => {
      for (let val of state.userHome) {
        if (val.homeId === payload) {
          val.available = false;
          break;
        }
      }
    },
    getCurrentHomeSuccess: (state, {payload}) => {
      state.current = payload;
    },
    setOfferSuccess: (state, {payload}) => {
      state.current.hasApplied = true;

      for (let val of state.data) {
        if (val.homeId === payload) {
          val.hasApplied = true;
          break;
        }
      }
    },
    getOfferSuccess: (state, {payload}) => {
      state.offer = payload;
    },
    getFavSearchSuccess: (state, {payload}) => {
      state.favSearch = payload;
    },
    setFavSearchSuccess: (state, {payload}) => {
      state.favSearch.push(payload);
    },
    getAllFavSuccess: (state, {payload}) => {
      state.favHome = payload;
    },
    setFavHomeSuccess: (state, {payload}) => {
      if (state.data) {
        for (let i = 0; i < state.data.length; i++) {
          if (state.data[i].homeId === payload.homeId) {
            state.data[i].isFav = true;
            break;
          }
        }
      }

      if (state.favHome) {
        payload.isFav = true;
        state.favHome.push(payload);
      }
    },
    removeFavHomeSuccess: (state, {payload}) => {
      if (state.data) {
        for (let i = 0; i < state.data.length; i++) {
          if (state.data[i].homeId === payload) {
            state.data[i].isFav = false;
            break;
          }
        }
      }

      if (state.favHome) {
        state.favHome = state.favHome.filter(({homeId}) => homeId !== payload);
      }
    },
    logoutSuccess: (state) => {
      state.data = null;
      state.current = null;
      state.favHome = null;
      state.userHome = null;
      state.offer = null;
      state.favSearch = null;
    },
  },
});

export default homeSlice.reducer;
